package com.aztekstudios.quit.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.Helper
import com.aztekstudios.quit.util.Solver
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ProgressViewModel : ViewModel() {

    fun usageBarData(c: Context): BarDataSet {
        val list: MutableList<BarEntry> = ArrayList()
        val pref = DataManager(c)
        val list1 = Solver().StringToList(pref.read(C.PREF_USAGE))
        when {
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
        val colors = intArrayOf(
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor()
        )
        val barSet = BarDataSet(list, "UsageStatistics")
        barSet.colors = colors.toMutableList()
        return barSet
    }

    fun unlocksBarData(c: Context): BarDataSet {
        val list: MutableList<BarEntry> = ArrayList()
        val pref = DataManager(c)
        val list1 = Solver().StringToList(pref.read(C.PREF_UNLOCKS))
        when {
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
        val colors = intArrayOf(
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor(),
            Helper().generateRandomColor()
        )
        val barSet = BarDataSet(list, "UnlockStatistics")
        barSet.colors = colors.toMutableList()
        return barSet
    }
}