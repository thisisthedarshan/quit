package com.aztekstudios.quit.util

import android.content.Context
import java.util.*
import java.util.concurrent.TimeUnit


class DataManager(cx: Context) {
    private val c = cx
    fun write(key: String, data: String) {
        val sharedPref = c.getSharedPreferences(C.PREFS_APP, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, data)
            apply()
        }
    }

    fun read(key: String): String {
        return c.getSharedPreferences(C.PREFS_APP, Context.MODE_PRIVATE).getString(key, "0")!!
    }

    fun update(key: String, data: String) {
        val date = Helper().getDateToday()
        val lastReadParam = read(C.PREF_LASTSAVED)
        if (lastReadParam == date) {
            val d = Solver().StringToList(read(key)).toMutableList()
            val counter = d.last().toInt() + data.toInt()
            d[d.lastIndex] = counter.toString()
            write(key, Solver().ListToString(d.toList()))
        } else if (lastReadParam != date && lastReadParam != "0") {
            val d = read(key) + "," + data
            write(key, d)
        } else {
            write(key, data)
        }
        write(C.PREF_LASTSAVED, date)
    }

    fun updateUsage(t: Long) {
        val date = Helper().getDateToday()
        val lastReadParam = read(C.PREF_LASTSAVED)
        if (lastReadParam == date) { // Update for today !
            val d = Solver().StringToList(read(C.PREF_USAGE)).toMutableList()
            val lastRecord = read(C.PREF_USAGE).toFloat()
            val timeTemp = read(C.PREF_TIME_TEMP).toLong()
            if (lastRecord >= 60) {
                write(C.PREF_TIME_TYPE, C.TIME_TYPE_HRS)
                write(C.PREF_USAGE, Solver().getTime(TimeUnit.HOURS, timeTemp).toString())
            }
            val type = read(C.PREF_TIME_TYPE)
            val timeTotal =
                if (type == C.TIME_TYPE_MINS || C.PREF_TIME_TYPE == "0") {
                    Solver().getTime(TimeUnit.MINUTES, timeTemp + t) // Its just minutes
                } else {
                    Solver().getTime(
                        TimeUnit.HOURS,
                        timeTemp + t
                    ) // get hours from temporarily stored long timing
                }
            d[d.lastIndex] = timeTotal.toString()
            write(C.PREF_USAGE, Solver().ListToString(d.toList()))
            write(C.PREF_TIME_TEMP, (timeTemp + t).toString())
        }
        // New Day !
        else if (lastReadParam != date && lastReadParam != "0") {
            write(C.PREF_TIME_TYPE, C.TIME_TYPE_MINS)
            val d = read(C.PREF_USAGE) + "," + Solver().getTime(TimeUnit.MINUTES, t).toString()
            write(C.PREF_USAGE, d)
            write(C.PREF_TIME_TEMP, t.toString())
        }
        // Completely New !
        else {
            write(C.PREF_USAGE, Solver().getTime(TimeUnit.MINUTES, t).toString())
            write(C.PREF_TIME_TYPE, C.TIME_TYPE_MINS)
            write(C.PREF_TIME_TEMP, t.toString())
        }
        write(C.PREF_LASTSAVED, date) // Update last saved !
    }

    fun updateAge() {
        val created = read(C.PROFILE_SIGNUP_YEAR)
        val cAge = read(C.PROFILE_SIGNUP_AGE).toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val newAge = cAge + (currentYear - created.toInt()) // Simple Math
        write(C.PROFILE_AGE, newAge.toString())
    }

}