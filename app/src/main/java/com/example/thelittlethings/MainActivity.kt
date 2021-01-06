package com.example.thelittlethings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val channelId = "THELITTLETHINGS_CHANNEL_ID"
    private val notificationId = 176

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        //TODO: will need a serverless function that saves a random reminder with the data to the database everyday,
        // and then we can read from the database, at a random time to get that day's task
        btnSend.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel() {
        //API 26+ needs a Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "The Little Things"
            val descriptionText = "Contains notifications for app The Little Things"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, channelId)
                //TODO: better small icon, large icon to distinguish, come up with color palatte
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle("Your reminder of the day!")
                //TODO: change to read from the database
            .setContentText("Complement the next person you see on their shoes!")
            .setOnlyAlertOnce(true)
            .setColor(1001280128)
            .setLargeIcon((BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground)))
        //TODO: add a pending intent to have the notification expand to read more text, and when clicked, go to the app

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun repeatDailyAtRandom() {
        //TODO: figure out how to send at random times of the day
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, Random.nextInt(9, 21))
        }
    }
}