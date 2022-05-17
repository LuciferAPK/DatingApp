package com.example.csplusapp.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.csplusapp.MyApplication
import com.example.csplusapp.R
import com.example.csplusapp.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        val notification = remoteMessage.notification ?: return
//
//        val strTitle = notification.title
//        val strMessage = notification.body

        //Data message
        val stringMap = remoteMessage.data
        val title = stringMap.get("user_name")
        val body = stringMap.get("description")
        senNotification(title, body)
    }

    private fun senNotification(strTitle: String?, strMessage: String?) {
        val intent = Intent(this, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle(strTitle)
            .setContentText(strMessage)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentIntent(pendingIntent)

        val notification = notificationBuilder.build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        Log.d("adutagMYFB: " , token)
    }
}