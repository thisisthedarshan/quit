package com.aztekstudios.quit.handlers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.aztekstudios.quit.R
import com.aztekstudios.quit.ui.SplashScreen
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.Helper

// TODO - Future plan to implement custom notification layout
class NotificationHandler(c: Context) {
    private var context: Context = c
    private var notification: NotificationCompat.Builder

    //private var notificationLayout:RemoteViews
    //private var notificationLayoutExpanded:RemoteViews
    private val dt = Helper()

    init {
        createChannel()
        //notificationLayout= RemoteViews(c.packageName, R.layout.notification_collapsed)
        //notificationLayoutExpanded = RemoteViews(c.packageName, R.layout.notification_complete)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0x0a,
            Intent(context, SplashScreen::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //setData()

        notification = NotificationCompat.Builder(context, C.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            //.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            //.setCustomContentView(notificationLayout)
            //.setCustomBigContentView(notificationLayoutExpanded)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
            .setShowWhen(false)
    }

    fun deliver(): Notification {
        NotificationManagerCompat.from(context).notify(C.NOTIFICATION_ID, notification.build())
        return notification.build()
    }

    fun update() {
        //setData()
        deliver()
    }

    fun remove() {
        NotificationManagerCompat.from(context).cancel(C.NOTIFICATION_ID)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "QuitRunning"
            val descriptionText = "The Notification indicating the app is running"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(C.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setData() {
        //notificationLayout.setTextViewText(R.id.homeTimeUsageNotif1,dt.getTodayUsage(context))
        //notificationLayoutExpanded.setTextViewText(R.id.homeTimeUsageNotif2,dt.getTodayUsage(context))
        //notificationLayout.setTextViewText(R.id.homeUnlockTimeNotif1,dt.getTodayUnlocks(context))
        //notificationLayoutExpanded.setTextViewText(R.id.homeUnlockTimeNotif2,dt.getTodayUnlocks(context))
    }

}