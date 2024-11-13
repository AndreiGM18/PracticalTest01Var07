package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.*

class PracticalTest01Var07Service : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private val random = Random()
    private val broadcastIntent = Intent("ro.pub.cs.systems.eim.practicaltest01var07.RANDOM_VALUES")

    private val runnable = object : Runnable {
        override fun run() {
            val values = IntArray(4) { random.nextInt(100) }
            broadcastIntent.putExtra("field1", values[0])
            broadcastIntent.putExtra("field2", values[1])
            broadcastIntent.putExtra("field3", values[2])
            broadcastIntent.putExtra("field4", values[3])

            // Send the local broadcast
            LocalBroadcastManager.getInstance(this@PracticalTest01Var07Service).sendBroadcast(broadcastIntent)

            handler.postDelayed(this, 3000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
