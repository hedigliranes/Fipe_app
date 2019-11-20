package com.example.consultafipe.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.consultafipe.R
import com.example.consultafipe.activities.FavActivity

object PriceNotification {

    val CHANNEL_ID = "default"

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(ctx : Context){
        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelName = "Standard"
        val channelDescription = "Canal default de notifications"

        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.GREEN
            vibrationPattern = longArrayOf(100,200,300,400,500,400,300,200,400)
        }

        notificationManager.createNotificationChannel(channel)
    }

    fun getContentIntent(context: Context): PendingIntent?{
        val intent = Intent(context,FavActivity::class.java)

        return TaskStackBuilder.create(context).addNextIntentWithParentStack(intent)
            .getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun notificationWithAction(ctx: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(ctx)
        }

        val notificationBuilder = NotificationCompat.Builder(ctx, CHANNEL_ID).setSmallIcon(R.drawable.ic_swap_vert_black_24dp)
            .setContentTitle("Variação de Preço !")
            .setContentText("Alguns veiculos mudaram de preço, confira !")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ActivityCompat.getColor(ctx,R.color.colorPrimaryDark))
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(getContentIntent(ctx))
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(ctx)

        notificationManager.notify(2,notificationBuilder.build())
    }

}