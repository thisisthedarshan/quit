package com.aztekstudios.quit.observers

import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import com.aztekstudios.quit.models.CustomParams
import com.aztekstudios.quit.util.Helper
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit

class ApplicationObserver constructor(context: Context) {
    val cx = context
    fun get(): Flowable<String> {
        return Flowable.interval(100, TimeUnit.MILLISECONDS) // Per 100 ms
            .map {
                var uEvent: UsageEvents.Event? = null
                val uMgr = cx.getSystemService(Service.USAGE_STATS_SERVICE) as UsageStatsManager
                val time = System.currentTimeMillis()
                val uEv = uMgr.queryEvents(time - 1000 * 3600, time)
                val ev = UsageEvents.Event()
                while (uEv.hasNextEvent()) {
                    uEv.getNextEvent(ev)
                    if (ev.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                        uEvent = ev
                    }
                }
                CustomParams(uEvent) // A Wrap-up of the event
            }
            .filter {
                it.event != null
            }
            .map {
                it.event
            }
            .filter {
                it?.packageName != null
            }
            .map {
                it!!.packageName
            }
            .filter {
                it != cx.packageName
            }
            .filter { it.startsWith("com.android", true).not() }
            .filter { Helper().getAppName(cx, it).contains("system", true).not() }
            .filter { Helper().getAppName(cx, it).contains("android", true).not() }
            .map {
                it
            }
            .distinctUntilChanged()
    }
}

/*
// from usagewrapup
.filter{
                it.event != null
            }
            .map {
                it.event
            }
            .filter{ it.packageName != null }
            .map {
                it
            }
            .filter{ it.packageName.contains(cx.packageName).not() }
            .filter{ Helper().getAppName(cx,it.packageName).contains("System",true).not() }
            .filter{ it.packageName.startsWith("com.android").not() }
            .filter{ it.packageName.contains("launcher").not() }
            .map {
                it.packageName
            }
 */