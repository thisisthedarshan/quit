/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "ApplicationObserver.kt" is a part of the project "Quit"                                    *
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

package com.aztekstudios.quit.observers

import android.app.Service
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import com.aztekstudios.quit.models.CustomParams
import com.aztekstudios.quit.util.Helper
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit

/**
 * Class made with ReactiveX Programming that runs in background to detect application launches.
 */
class ApplicationObserver constructor(context: Context) {
    val cx = context // Context variable

    /**
     * Obtain the flowable object results.
     */
    fun get(): Flowable<String> {
        return Flowable.interval(100, TimeUnit.MILLISECONDS) // Per 100 ms
            .map {
                // Get events
                var uEvent: UsageEvents.Event? = null

                // UsageStatsManager Instance
                val uMgr = cx.getSystemService(Service.USAGE_STATS_SERVICE) as UsageStatsManager

                // Current time
                val time = System.currentTimeMillis()

                // Query for recent events
                val uEv = uMgr.queryEvents(time - 1000 * 3600, time)

                // Load the event
                val ev = UsageEvents.Event()

                // Check for the event
                while (uEv.hasNextEvent()) {
                    uEv.getNextEvent(ev)

                    // Check if the application was launched.
                    if (ev.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                        uEvent = ev
                    }
                }
                CustomParams(uEvent) // A Wrap-up of the event
            }
            .filter {
                // Filter the event to make sure that it is not empty.
                it.event != null
            }
            .map {
                // If satisfied, pass the event
                it.event
            }
            .filter {
                // Check if the packagename corresponding to event is not null
                it?.packageName != null
            }
            .map {
                // Pass the packagename
                it!!.packageName
            }
            .filter {
                // Check if packagename is not of the application. This would complicate the usage pattern
                it != cx.packageName
            }
                // Check for other parameters and ensure that it doesn't belong to system
            .filter { it.startsWith("com.android", true).not() }
            .filter { Helper().getAppName(cx, it).contains("system", true).not() }
            .filter { Helper().getAppName(cx, it).contains("android", true).not() }
            .map {
                // Map the final packagename
                it
            }
            .distinctUntilChanged() // Refer Javadoc for more
    }
}