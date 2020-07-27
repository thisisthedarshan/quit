/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Solver.kt" is a part of the project "Quit"                                                 *
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

package com.aztekstudios.quit.util

import android.content.Context
import java.util.concurrent.TimeUnit

/**
 * Algorithms Class
 */
class Solver {
    /**
     * Converts a given list to a string separated by a comma
     * @param lst List<String> to convert to string
     * @return String equivalent
     */
    fun listToString(lst: List<String>): String {
        return lst.joinToString(",")
    }

    /**
     * Converts a given string into a list of strings
     * @param data Sting value to convert to list
     * @return List of strings of individual values from given string
     */
    fun stringToList(data: String): List<String> {
        return data.split(",")
    }

    /**
     * Obtains the author from given quote. This is because the input contains both quote and it's sayers
     */
    fun getAuthorFromQuote(quote: String): String {
        return quote.substringAfter("~")
    }

    /**
     * Obtains the quote from given input. This is because the input contains both quote and it's sayers
     */
    fun getTheQuote(quote: String): String {
        return quote.substringBefore("~")
    }

    /**
     * Obtains the average of unlocks
     * @param unlocks List of unlocks data
     * @return Average unlocks Int
     */
    fun getAverageUnlocks(unlocks: List<String>): Int {
        val count = unlocks.size
        var total = 0
        unlocks.forEach {
            total += it.toInt()
        }
        return (total / count)
    }

    /**
     * Obtains the average of usage statistics
     * @param usage List of usage data
     * @return Float average of the given data
     */
    fun getAverageUsage(usage: List<String>): Float {
        val count = usage.size
        var total = (0).toFloat()
        usage.forEach {
            val temp = it.toFloat()
            total += temp.toInt()
        }
        return (total / count).toFloat()
    }

    /**
     * Function to decide which badge is given to the user. Updates daily :)
     */
    fun badger(context: Context) {
        val pref =
            DataManager(context)                                            // Data Manager Class instance
        val usageGoal =
            pref.read(C.PREF_GOAL_USAGE).toFloat()                          // The unlocks goal set by user
        val unlockGoal =
            pref.read(C.PREF_GOAL_UNLOCKS).toFloat()                        // The usage goal set by user
        val avgUnlocksLst = pref.read(C.PREF_UNLOCKS)                // Average Unlocks
        val avgUsageLst = pref.read(C.PREF_USAGE)                    // Average Usage

        val avgUsage = getAverageUsage(stringToList(avgUsageLst))
        val avgUnlocks = getAverageUnlocks(stringToList(avgUnlocksLst))

        var badge = C.BADGE_1                                         // Badge

        badge = if (avgUnlocks > unlockGoal || avgUsage > usageGoal) {
            C.BADGE_1
        } else if ((avgUnlocks > unlockGoal - C.BADGE_STAGE_A1 && avgUnlocks < unlockGoal) || (avgUsage > usageGoal - C.BADGE_STAGE_B1 && avgUnlocks < unlockGoal)) {
            C.BADGE_2
        } else if ((avgUnlocks > unlockGoal - C.BADGE_STAGE_A2 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A1) || (avgUsage > usageGoal - C.BADGE_STAGE_B2 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A1)) {
            C.BADGE_3
        } else if ((avgUnlocks > unlockGoal - C.BADGE_STAGE_A3 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A2) || (avgUsage > usageGoal - C.BADGE_STAGE_B3 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A2)) {
            C.BADGE_4
        } else if ((avgUnlocks > unlockGoal - C.BADGE_STAGE_A4 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A3) || (avgUsage > usageGoal - C.BADGE_STAGE_B4 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A3)) {
            C.BADGE_5
        } else if ((avgUnlocks > unlockGoal - C.BADGE_STAGE_A5 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A4) || (avgUsage > usageGoal - C.BADGE_STAGE_B5 && avgUnlocks < unlockGoal - C.BADGE_STAGE_A4)) {
            C.BADGE_TOP
        } else {
            C.BADGE_1
        }

        // Write to database
        pref.write(C.PREF_BADGE, badge)
    }

    /**
     * Converts given long value into time
     * @param unit TimeUnit type to return - Whether the data should be time in minutes or hours
     * @param timeDifference The milliseconds long value to convert into time
     * @return Float time corresponding the given long value
     */
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
}