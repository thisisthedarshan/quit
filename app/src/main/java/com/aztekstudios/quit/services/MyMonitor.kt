/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "MyMonitor.kt" is a part of the project "Quit"                                              *
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

package com.aztekstudios.quit.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.aztekstudios.quit.R
import com.aztekstudios.quit.handlers.NotificationHandler
import com.aztekstudios.quit.handlers.ServiceMaker
import com.aztekstudios.quit.observers.ApplicationObserver
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.QuoteFactory
import com.aztekstudios.quit.util.Solver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Service to handle the background data collection. PS, not private data is collected!
 *
 */
class MyMonitor : Service() {

    // We start by defining the variables and values

    private lateinit var notifHandler: NotificationHandler

    private var appMonDisposable: Disposable? = null
    private var popupDisposable: Disposable? = null
    private val completeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var observer: ApplicationObserver

    // Future implementation for showing which app was used for how much of the time
    private var appPkg = packageName
    private var usageTime: Long = 0
    private var timeoutPopup = 0

    // ?
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * Start the magic !
     */
    override fun onCreate() {
        super.onCreate()
        //Toast.makeText(applicationContext,"Service Created",Toast.LENGTH_LONG).show()
        // We initialize some stuff here
        observer = ApplicationObserver(applicationContext)
        timeoutPopup = DataManager(applicationContext).read(C.PREF_TIMEOUT).toInt()
        if (timeoutPopup == 0) {
            timeoutPopup = 15 // Set it as a default one
        }
        // We simplify the initialization by defining their own functions
        startNotificationHandler()
        registerReceivers()
        startObserver()
    }

    /**
     * Nothing much to do !
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    /**
     * Tidy-up the mess ;)
     */
    override fun onDestroy() {
        ServiceMaker().bake(applicationContext)
        deregisterReceivers()
        if (completeDisposable.isDisposed.not()) {
            completeDisposable.dispose()
        }
        notifHandler.remove()
        Toast.makeText(applicationContext, "Quit Service Died", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }

    /**
     *
     * Rest all other functions are defined here
     *
     */

// First, we start with the service broadcasts

    /**
     * The broadcast receiver that handles the SCREEN_ON and SCREEN_OFF events
     */
    private val lockUnlock = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_ON) {
                DataManager(context!!).update(C.PREF_UNLOCKS, "1")
                startObserver()
                //Toast.makeText(applicationContext,"Unlocked Device",Toast.LENGTH_LONG).show()
            } else if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                stopObserver()
                //stopClosedObserver()
                stopPopups()
            }
        }
    }

    // Then we define rest of the functions

    /**
     * Function to start notification and deliver it.
     */
    private fun startNotificationHandler() {
        notifHandler = NotificationHandler(applicationContext)
        val notif = notifHandler.deliver()
        startForeground(C.NOTIFICATION_ID, notif) // Bind as always-running
    }

    /**
     * Function to start receivers. These will handle the events like screen-on and screen-off
     */
    private fun registerReceivers() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(lockUnlock, filter)
    }

    /**
     * Function to de-register the receivers
     */
    private fun deregisterReceivers() {
        unregisterReceiver(lockUnlock) // Just this.
    }

    /**
     * Function to start observing application launches
     */
    private fun startObserver() {
        if (appMonDisposable != null && appMonDisposable?.isDisposed?.not() == true) return
        appMonDisposable = observer
            .get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // Handle the event here
                    appPkg = if (it.contains("launcher", true)) {
                        stopPopups()
                        finishMonitorUsage()
                        ""
                    } else {
                        startPopups()
                        monitorUsage()
                        it
                    }
                    // Toast.makeText(applicationContext,"Application package = $it",Toast.LENGTH_SHORT).show() // Debugging
                    //startPopups()
                    //startClosedObserver()
                },
                {
                    Toast.makeText(
                        applicationContext,
                        "StartObserverError\n" + it.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        completeDisposable.add(appMonDisposable)
    }

    /**
     * Function to stop observing application launches
     */
    private fun stopObserver() {
        if (appMonDisposable != null && appMonDisposable?.isDisposed?.not() == true) {
            appMonDisposable?.dispose() // Dispose the object
        }
        finishMonitorUsage()
    }

    /**
     * Function to start warning popups
     */
    private fun startPopups() {
        if (popupDisposable != null && popupDisposable?.isDisposed?.not() == true) return
        //Toast.makeText(applicationContext,"Started Popup service for $timeoutPopup",Toast.LENGTH_SHORT).show()
        popupDisposable = Observable.interval(timeoutPopup.toLong(), TimeUnit.MINUTES)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showPopup()     // It has been some time. Show the popup
            }
    }

    /**
     * Function to stop popups - Cleans the useless disposables
     */
    private fun stopPopups() {
        if (popupDisposable != null && popupDisposable?.isDisposed?.not() == true) {
            popupDisposable?.dispose()  // Dispose the popup object
            //Toast.makeText(applicationContext,"Stopped Popup service",Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Function to show popups
     */
    @SuppressLint("InflateParams", "InlinedApi")
    private fun showPopup() {
        //val appName = Helper().getAppName(applicationContext, appPkg)
        // Make a toast showing monitoring is active - for testing
        //Toast.makeText(applicationContext, "Monitoring $appName", Toast.LENGTH_LONG).show()

        // Here, we will show the popup
        /**
         * Window Manager Parameters
         */
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.OPAQUE
        )

        // For older versions, we set the type from WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY to WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

        //Get instance of the WindowManager and Layout inflater
        val wm =
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val d: View = inflater.inflate(R.layout.activity_pop_up, null)
        // Add layout to window manager
        val messageText =
            "It has been more than $timeoutPopup minutes since you have been using the device. Why not take some rest?" // Message
        // Get quote
        val quoteTemp = QuoteFactory().getRandomQuote()
        val quote = Solver().getTheQuote(quoteTemp)
        val author = Solver().getAuthorFromQuote(quoteTemp)
        // Find Views
        val quoteText = d.findViewById<TextView>(R.id.quote)
        val quoteAuthor = d.findViewById<TextView>(R.id.author)
        val messageView = d.findViewById<TextView>(R.id.messagePopup)
        val okButton = d.findViewById<Button>(R.id.okPopup)
        // Set values
        quoteAuthor!!.text = author
        quoteText!!.text = quote
        messageView!!.text = messageText
        okButton!!.setOnClickListener {
            wm.removeView(d) // remove the view wen button is pressed
        }
        // Set gravity
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL

        //Show the popup window.
        wm.addView(d, params)
    }

    /**
     * Function to track application usage - Counts the time used
     */
    private fun monitorUsage() {
        usageTime = SystemClock.elapsedRealtime() // A work-around of getting usage time
    }

    /**
     * Function to calculate application usage - Counts the time used
     */
    private fun finishMonitorUsage() {
        //Toast.makeText(applicationContext, "Package = $appPkg", Toast.LENGTH_SHORT).show() // Debugging

        if (usageTime > 0 /*&& appPkg.isEmpty().not()*/) { // Making sure that the application was actually used !

            val uTime = SystemClock.elapsedRealtime() - usageTime        // Calculate delay
            val dataManager = DataManager(baseContext)                          // DataManager Instance
            dataManager.updateUsage(uTime)                                      // Automatically decides the type and write it to database.

            // Other methods
            //Toast.makeText(applicationContext, "Total usage = $uTime", Toast.LENGTH_SHORT).show()
            //val mgr = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            //val usageMap = mgr.queryAndAggregateUsageStats(usageTime,System.currentTimeMillis())
            //val mTime = DateUtils.formatElapsedTime(usageMap[appPkg]!!.totalTimeInForeground)
            //Toast.makeText(applicationContext, "Total usage = $mTime", Toast.LENGTH_SHORT).show()

            // Reset
            usageTime = 0
            appPkg = ""
        }
    }
}

