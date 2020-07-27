/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "ProgressViewModel.kt" is a part of the project "Quit"                                      *
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

package com.aztekstudios.quit.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.Helper
import com.aztekstudios.quit.util.Solver
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

/**
 * ViewModel for Progress Fragment
 */
class ProgressViewModel : ViewModel() {

    /**
     * Function that generates the Usage Data for Gra graph
     * @param c Context
     * @return BarDataSet of usage statistics
     */
    fun usageBarData(c: Context): BarDataSet {
        val usageList: MutableList<BarEntry> = ArrayList()                           // Array List for recording entries
        val prefM = DataManager(c)                                               // DataManager Class instance
        val prefUnlocksList = Solver().stringToList(prefM.read(C.PREF_USAGE))  // List of Usage Statistics
        when {                                                                  // We want only last 7 values, so this work.
            prefUnlocksList.size > 7 -> {
                var a = prefUnlocksList.lastIndex - 7
                for (b in 0..6) {
                    val data = prefUnlocksList[a].toFloat()
                    usageList.add(BarEntry(b.toFloat(), data))
                    a++
                }
            }
            prefUnlocksList.size <= 7 -> {
                var pos1 = 0f
                for (d in prefUnlocksList) {
                    usageList.add(BarEntry(pos1, d.toFloat()))
                    pos1++
                }
            }
        }

        // Generate random colors
        val colors = intArrayOf(
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor()
        )

        // Generate BarDataSet
        val usageStatsBar = BarDataSet(usageList, "UsageStatistics")    // Generate BarDataSet
        usageStatsBar.colors = colors.toMutableList()                    // Set color

        return usageStatsBar                                             // Return the dataset
    }

    /**
     * Function that generates the Unlocks Data for Gra graph
     * @param c Context
     * @return BarDataSet of unlocks statistics
     */
    fun unlocksBarData(c: Context): BarDataSet {
        val unlocksList: MutableList<BarEntry> = ArrayList()                               // Array List for recording entries
        val prefMan = DataManager(c)                                                   // DataManager Class instance
        val prefsUnlockList = Solver().stringToList(prefMan.read(C.PREF_UNLOCKS))    // List of Unlocks Statistics
        when {                                                                      // We want only last 7 values, so this work.
            prefsUnlockList.size > 7 -> {
                var e = prefsUnlockList.lastIndex - 7
                for (d in 0..6) {
                    val data = prefsUnlockList[e].toFloat()
                    unlocksList.add(BarEntry(d.toFloat(), data))
                    e++
                }
            }
            prefsUnlockList.size <= 7 -> {
                var pos2 = 0f
                for (d in prefsUnlockList) {
                    unlocksList.add(BarEntry(pos2, d.toFloat()))
                    pos2++
                }
            }
        }

        // Generate random colors
        val colors = intArrayOf(
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor()
        )
        val unlockStatsBar = BarDataSet(unlocksList, "UnlockStatistics")   // Generate BarDataSet
        unlockStatsBar.colors = colors.toMutableList()                    // Set color
        return unlockStatsBar                                             // Return the dataset
    }
}