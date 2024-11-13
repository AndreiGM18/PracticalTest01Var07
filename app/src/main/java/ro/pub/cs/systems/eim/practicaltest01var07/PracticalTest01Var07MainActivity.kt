package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PracticalTest01Var07MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    private var sum: Int = 0
    private var product: Int = 1

    private lateinit var field1: EditText
    private lateinit var field2: EditText
    private lateinit var field3: EditText
    private lateinit var field4: EditText

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val value1 = intent?.getIntExtra("field1", 0) ?: 0
            val value2 = intent?.getIntExtra("field2", 0) ?: 0
            val value3 = intent?.getIntExtra("field3", 0) ?: 0
            val value4 = intent?.getIntExtra("field4", 0) ?: 0

            field1.setText(value1.toString())
            field2.setText(value2.toString())
            field3.setText(value3.toString())
            field4.setText(value4.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var07_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        field1 = findViewById(R.id.editText)
        field2 = findViewById(R.id.editText2)
        field3 = findViewById(R.id.editText3)
        field4 = findViewById(R.id.editText4)

        val setButton: Button = findViewById(R.id.button)
        setButton.setOnClickListener {
            val field1Text = field1.text.toString()
            val field2Text = field2.text.toString()
            val field3Text = field3.text.toString()
            val field4Text = field4.text.toString()

            if (field1Text.isNotEmpty() && field2Text.isNotEmpty() && field3Text.isNotEmpty() && field4Text.isNotEmpty()) {
                try {
                    val value1 = field1Text.toInt()
                    val value2 = field2Text.toInt()
                    val value3 = field3Text.toInt()
                    val value4 = field4Text.toInt()

                    val intent = Intent(this, PracticalTest01Var07SecondaryActivity::class.java)
                    intent.putExtra("field1", value1)
                    intent.putExtra("field2", value2)
                    intent.putExtra("field3", value3)
                    intent.putExtra("field4", value4)
                    startActivityForResult(intent, REQUEST_CODE)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "All fields must contain numbers", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }

        startService(Intent(this, PracticalTest01Var07Service::class.java))

        // Register the local broadcast receiver
        val intentFilter = IntentFilter("ro.pub.cs.systems.eim.practicaltest01var07.RANDOM_VALUES")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getIntExtra("result", 0) ?: 0
            Toast.makeText(this, "Result: $result", Toast.LENGTH_LONG).show()
            sum = result
            product = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("sum", sum)
        outState.putInt("product", product)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sum = savedInstanceState.getInt("sum")
        product = savedInstanceState.getInt("product")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop service and unregister the local receiver
        stopService(Intent(this, PracticalTest01Var07Service::class.java))
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}
