package com.aztekstudios.quit.util

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.util.*
import kotlin.collections.ArrayList


class UsageRecorder(context: Context) {
    private val c = context
    private val umgr: UsageStatsManager =
        c.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private val pkgMger = c.packageManager

    private fun createAppList(): List<String> {
        val launcahbles = Intent(Intent.ACTION_MAIN)
        launcahbles.addCategory(Intent.CATEGORY_LAUNCHER)
        val lst = pkgMger.queryIntentActivities(launcahbles, PackageManager.GET_META_DATA)
        val pkgsWithIntent: MutableList<String> = ArrayList()
        for (mRinfo in lst) {
            pkgsWithIntent.add(mRinfo.activityInfo.packageName)
        }
        val list: MutableList<String> = ArrayList()
        val pkgLst = pkgMger.getInstalledPackages(0)
        for (pkgInfo in pkgLst) {
            // Check if system app?
            if ((pkgInfo.applicationInfo.flags.and(ApplicationInfo.FLAG_SYSTEM)) == 0) {
                // Check if it is accessible by launcher ( We don't want background apps or service providers
                if (pkgsWithIntent.contains(pkgInfo.packageName)) {
                    list.add(pkgInfo.packageName)
                }
            }
        }
        return list.toList()
    }

    fun getTotalUsageTime(): Float {
        var time = (0).toFloat()
        val usageStatsMap = umgr.queryAndAggregateUsageStats(
            getStartOfDay(),
            System.currentTimeMillis()
        )
        val lst = createAppList()
        val usageStats: MutableList<UsageStats> = ArrayList()
        usageStats.addAll(usageStatsMap.values)
        if (usageStats.isNotEmpty()) {
            for (stat in usageStats) {
                if (lst.contains(stat.packageName)) {
                    try {
                        time += stat.totalTimeInForeground
                    } catch (e: Exception) {
                        // Do Noting for now.
                    }
                }
            }
        }
        return time
    }

    private fun getStartOfDay(): Long {
        /*val timeNow = Date().time
        val dt = Date(timeNow - timeNow % (24 * 60 * 60 * 1000))*/
        val d: Calendar = GregorianCalendar()
        d.set(Calendar.HOUR_OF_DAY, 0)
        d.set(Calendar.MINUTE, 0)
        d.set(Calendar.SECOND, 0)
        d.set(Calendar.MILLISECOND, 0)
        return d.timeInMillis
    }
}
