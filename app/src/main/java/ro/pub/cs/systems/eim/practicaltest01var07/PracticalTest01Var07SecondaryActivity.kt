package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_var07_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val field1 = findViewById<TextView>(R.id.editTextSec)
        val field2 = findViewById<TextView>(R.id.editTextSec2)
        val field3 = findViewById<TextView>(R.id.editTextSec3)
        val field4 = findViewById<TextView>(R.id.editTextSec4)

        val value1 = intent.getIntExtra("field1", 0)
        val value2 = intent.getIntExtra("field2", 0)
        val value3 = intent.getIntExtra("field3", 0)
        val value4 = intent.getIntExtra("field4", 0)

        field1.text = value1.toString()
        field2.text = value2.toString()
        field3.text = value3.toString()
        field4.text = value4.toString()

        val sumButton = findViewById<Button>(R.id.buttonSecSum)
        val productButton = findViewById<Button>(R.id.buttonSecProd)

        sumButton.setOnClickListener {
            val sum = value1 + value2 + value3 + value4
            val resultIntent = Intent()
            resultIntent.putExtra("result", sum)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        productButton.setOnClickListener {
            val product = value1 * value2 * value3 * value4
            val resultIntent = Intent()
            resultIntent.putExtra("result", product)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}