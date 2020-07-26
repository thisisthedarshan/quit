package com.aztekstudios.quit.util

import android.content.Context
import java.util.concurrent.TimeUnit

/**
 * Algorithms Class
 */
class Solver {
    fun ListToString(lst: List<String>): String {
        return lst.joinToString(",")
    }

    fun StringToList(data: String): List<String> {
        return data.split(",")
    }

    fun getAuthorFromQuote(quote: String): String {
        return quote.substringAfter("~")
    }

    fun justGetTheQuote(quote: String): String {
        return quote.substringBefore("~")
    }

    fun getAverageUnlocks(unlocks: List<String>): Int {
        val count = unlocks.size
        var total = 0
        unlocks.forEach {
            val temp = it.toFloat()
            total += temp.toInt()
        }
        return (total / count).toInt()
    }

    fun getAverageUsage(usage: List<String>): Float {
        val count = usage.size
        var total = (0).toFloat()
        usage.forEach {
            val temp = it.toFloat()
            total += temp.toInt()
        }
        return (total / count).toFloat()
    }

    fun filterData(newPkg: String, context: Context): Boolean {
        return newPkg.contains("launcher", true) ||
                Helper().getAppName(context, newPkg).contains("launcher", true)
    }

    /**
     * Function to decide which badge is given to the user. Updates daily :)
     */
    fun badger(context: Context) {
        val pref = DataManager(context)
        val usageGoal = pref.read(C.PREF_GOAL_USAGE).toFloat()
        val unlockGoal = pref.read(C.PREF_GOAL_UNLOCKS).toFloat()
        val avgUnlocksLst = pref.read(C.PREF_UNLOCKS)
        val avgUsageLst = pref.read(C.PREF_USAGE)
        val avgUsage = getAverageUsage(StringToList(avgUsageLst))
        val avgUnlocks = getAverageUnlocks(StringToList(avgUnlocksLst))
        var badge = C.BADGE_1

        badge = if (avgUnlocks > unlockGoal || avgUsage > usageGoal) {
            C.BADGE_1
        } else if ((avgUnlocks > unlockGoal - C.BADGE_ASTAGE1 && avgUnlocks < unlockGoal) || (avgUsage > usageGoal - C.BADGE_BSTAGE1 && avgUnlocks < unlockGoal)) {
            C.BADGE_2
        } else if ((avgUnlocks > unlockGoal - C.BADGE_ASTAGE2 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE1) || (avgUsage > usageGoal - C.BADGE_BSTAGE2 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE1)) {
            C.BADGE_3
        } else if ((avgUnlocks > unlockGoal - C.BADGE_ASTAGE3 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE2) || (avgUsage > usageGoal - C.BADGE_BSTAGE3 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE2)) {
            C.BADGE_4
        } else if ((avgUnlocks > unlockGoal - C.BADGE_ASTAGE4 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE3) || (avgUsage > usageGoal - C.BADGE_BSTAGE4 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE3)) {
            C.BADGE_5
        } else if ((avgUnlocks > unlockGoal - C.BADGE_ASTAGE5 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE4) || (avgUsage > usageGoal - C.BADGE_BSTAGE5 && avgUnlocks < unlockGoal - C.BADGE_ASTAGE4)) {
            C.BADGE_TOP
        } else {
            C.BADGE_1
        }
        pref.write(C.PREF_BADGE, badge)
    }

    fun getTime(unit: TimeUnit, timeDifference: Long): Float {
        val timeInSeconds: Float = timeDifference.toFloat() / 1000
        val hours: Float = timeInSeconds / 3600
        val minutes: Float = timeInSeconds / 60
        return when (unit) {
            TimeUnit.MINUTES -> minutes
            TimeUnit.HOURS -> hours
            else -> minutes
        }
    }

    fun getHourFromMinutes(mins: Float): Float {
        return mins / 60.toFloat()
    }
}