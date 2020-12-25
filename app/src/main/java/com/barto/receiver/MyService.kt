package com.barto.receiver

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {

    private var id = 0

    override fun onCreate() {
        super.onCreate()
        createChannel(applicationContext)
        val notification = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.channelID))
                .setContentTitle("title")
                .setTicker("title")
                .setContentText("service running!")
                .setAutoCancel(true)
                .build()
        startForeground(0, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            Toast.makeText(applicationContext, "onStart() is called!", Toast.LENGTH_SHORT).show()
//            startForeground(id, Notification())
            createChannel(applicationContext)
            createNotification(intent)
        }
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun createNotification(intent: Intent){
        val context = applicationContext
        val list = Intent()
        list.component = ComponentName("com.barto.simplecrud", "com.barto.simplecrud.ListActivity")

        list.putExtra("productid", (intent.getStringExtra("productid")))
        list.putExtra("productname", (intent.getStringExtra("productname")))
        list.putExtra("productprice", (intent.getStringExtra("productprice")))
        list.putExtra("productnumber", (intent.getStringExtra("productnumber")))
        list.putExtra("productisbought", (intent.getStringExtra("productisbought")))

        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            list,
            PendingIntent.FLAG_ONE_SHOT
        )

        //builder
        val notification = NotificationCompat.Builder(context, context.getString(R.string.channelID))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Added new product: ")
            .setContentText(intent.getStringExtra("productname"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        startForeground(++id, notification)
//        NotificationManagerCompat.from(context).notify(id, notification)
    }

    fun createChannel(context: Context){
        val channel = NotificationChannel(
                context.getString(R.string.channelID),
                context.getString(R.string.channelName),
                NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}