package com.example.waterreminder

import android.app.*
import android.content.*
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40,40,40,40)

        val intervalInput = EditText(this)
        intervalInput.hint = "提醒间隔（分钟）"
        intervalInput.setText("60")

        val startButton = Button(this)
        startButton.text = "开始提醒"

        layout.addView(intervalInput)
        layout.addView(startButton)

        setContentView(layout)

        startButton.setOnClickListener {
            val minutes = intervalInput.text.toString().toLongOrNull() ?: 60
            val intent = Intent(this, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intervalMillis = minutes * 60 * 1000

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                intervalMillis,
                pendingIntent
            )

            Toast.makeText(this, "提醒已启动", Toast.LENGTH_SHORT).show()
        }
    }
}
