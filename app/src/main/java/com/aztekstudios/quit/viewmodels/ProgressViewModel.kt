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
        val list: MutableList<BarEntry> = ArrayList()                           // Array List for recording entries
        val pref = DataManager(c)                                               // DataManager Class instance
        val list1 = Solver().stringToList(pref.read(C.PREF_USAGE))  // List of Usage Statistics
        when {                                                                  // We want only last 7 values, so this work.
            list1.size > 7 -> {
                var x = list1.lastIndex - 7
                for (y in 0..6) {
                    val data = list1[x].toFloat()
                    list.add(BarEntry(y.toFloat(), data))
                    x++
                }
            }
            list1.size <= 7 -> {
                var pos = 0f
                for (d in list1) {
                    list.add(BarEntry(pos, d.toFloat()))
                    pos++
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
        val barSet = BarDataSet(list, "UsageStatistics")    // Generate BarDataSet
        barSet.colors = colors.toMutableList()                    // Set color

        return barSet                                             // Return the dataset
    }

    /**
     * Function that generates the Unlocks Data for Gra graph
     * @param c Context
     * @return BarDataSet of unlocks statistics
     */
    fun unlocksBarData(c: Context): BarDataSet {
        val list: MutableList<BarEntry> = ArrayList()                               // Array List for recording entries
        val pref = DataManager(c)                                                   // DataManager Class instance
        val list1 = Solver().stringToList(pref.read(C.PREF_UNLOCKS))    // List of Unlocks Statistics
        when {                                                                      // We want only last 7 values, so this work.
            list1.size > 7 -> {
                var x = list1.lastIndex - 7
                for (y in 0..6) {
                    val data = list1[x].toFloat()
                    list.add(BarEntry(y.toFloat(), data))
                    x++
                }
            }
            list1.size <= 7 -> {
                var pos = 0f
                for (d in list1) {
                    list.add(BarEntry(pos, d.toFloat()))
                    pos++
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
        val barSet = BarDataSet(list, "UnlockStatistics")   // Generate BarDataSet
        barSet.colors = colors.toMutableList()                    // Set color
        return barSet                                             // Return the dataset
    }
}