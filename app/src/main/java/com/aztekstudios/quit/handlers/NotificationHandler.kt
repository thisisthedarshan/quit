/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "NotificationHandler.kt" is a part of the project "Quit"                                    *
 *                                                                                                          *
 *     Quit is free software: you can redistribute it and/or modify                                         *
 *     it under the terms of the GNU General Public License as published by                                 *
 *     the Free Software Foundation, either version 3 of the License, or                                    *
 *     (at your option) any later version.                                                                  *
 *                                                                                                          *
 *     Project Quit is distributed in the hope that it will be useful,                                      *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of                                       *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                        *
 *     GNU General Public License for more details.                                                         *
 *                                                                                                          *
 *     You should have received a copy of the GNU General Public License                                    *
 *     along with Project Quit.  If not, see <https://www.gnu.org/licenses/>.                               *
 *                                                                                                          *
 ************************************************************************************************************/

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

/**
 *  This class handles the creation of notifications
 */
class NotificationHandler(c: Context) {

    // Variables
    private var context: Context = c
    private var notification: NotificationCompat.Builder

    // Future implementation
    //private var notificationLayout:RemoteViews
    //private var notificationLayoutExpanded:RemoteViews
    //private val dt = Helper()

    /**
     * When initialised with construction, do the following..
     */
    init {
        // Create a notification channel
        createChannel()
        //notificationLayout= RemoteViews(c.packageName, R.layout.notification_collapsed)
        //notificationLayoutExpanded = RemoteViews(c.packageName, R.layout.notification_complete)

        // Pending intent for starting app when notification is clicked
        val pendingIntent = PendingIntent.getActivity(
            context,
            0x0a,
            Intent(context, SplashScreen::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //setData()

        // Create the notification
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

    /**
     * Builds and delivers the notification in the system
     */
    fun deliver(): Notification {
        NotificationManagerCompat.from(context).notify(C.NOTIFICATION_ID, notification.build())
        return notification.build()
    }

    /**
     * Update the notification contents. Currently not implemented. By default, it just delivers the notification
     */
    fun update() {
        //setData()
        deliver()
    }

    /**
     * Removes the notification from system
     */
    fun remove() {
        NotificationManagerCompat.from(context).cancel(C.NOTIFICATION_ID)
    }

    /**
     * Create a notification channel. This is required since Android 8
     */
    private fun createChannel() {
        // Check if version is above Android 8 (Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Channel data
            val name = "QuitRunning"     // Name of the channel
            val descriptionText = "The Notification indicating the app is running"   // Description of the channel
            val importance = NotificationManager.IMPORTANCE_DEFAULT  // Importance of the notification

            // Setup channel
            val channel = NotificationChannel(C.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Create the channel
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Future Implementation
   /* private fun setData() {
        notificationLayout.setTextViewText(R.id.homeTimeUsageNotif1,dt.getTodayUsage(context))
        notificationLayoutExpanded.setTextViewText(R.id.homeTimeUsageNotif2,dt.getTodayUsage(context))
        notificationLayout.setTextViewText(R.id.homeUnlockTimeNotif1,dt.getTodayUnlocks(context))
        notificationLayoutExpanded.setTextViewText(R.id.homeUnlockTimeNotif2,dt.getTodayUnlocks(context))
    }*/

}

// TODO - Future plan to implement custom notification layout