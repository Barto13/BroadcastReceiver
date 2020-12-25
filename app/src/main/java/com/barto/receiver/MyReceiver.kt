package com.barto.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat



class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "wchodzi", Toast.LENGTH_SHORT).show()
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if(intent.action == context.getString(R.string.addProduct)){

            val i = Intent(context, MyService::class.java)
            i.putExtra("productid", (intent.getStringExtra("productId")))
            i.putExtra("productname", (intent.getStringExtra("name")))
            i.putExtra("productprice", (intent.getStringExtra("productPrice")))
            i.putExtra("productnumber", (intent.getStringExtra("productNumber")))
            i.putExtra("productisbought", (intent.getStringExtra("productIsBought")))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(i)
            } else {
                context.startService(i)
            }
        }
    }

}