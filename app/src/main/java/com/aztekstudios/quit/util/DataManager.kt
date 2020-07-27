/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "DataManager.kt" is a part of the project "Quit"                                            *
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
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Class handling data management and functions like read, write, update and so on.
 * Currently it implements Shared Preferences. Once I get hold of SQL, I'm planning to implement it here.
 *
 */
// Maybe we might use another improved database technology!
class DataManager(cx: Context) {
    private val c = cx // We need a context !

    /**
     * Write to database
     * @param key Key used to store the data
     * @param data Data to be written into database
     */
    fun write(key: String, data: String) {
        val sharedPref = c.getSharedPreferences(C.PREFS_APP, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, data)
            apply()
        }
    }

    /**
     * Read from database
     * @param key Key of the value to read
     * @return The data represented by the key
     */
    fun read(key: String): String {
        return c.getSharedPreferences(C.PREFS_APP, Context.MODE_PRIVATE).getString(key, "0")!!
    }

    /**
     * Update Unlock counts into database
     * @param key Key of data to write
     * @param data Data to be written
     */
    fun update(key: String, data: String) {

        val date = Helper().getDateToday()
        val lastReadParam = read(C.PREF_LASTSAVED)

        if (lastReadParam == date) { // If the function was requested the same day, we just need to increase the value.

            val d = Solver().stringToList(read(key)).toMutableList()

            // Increase the last value
            val counter = d.last().toInt() + data.toInt()

            // Last index contains the latest value. We are updating it
            d[d.lastIndex] = counter.toString()

            // Write the data in database
            write(key, Solver().listToString(d.toList()))

        } else if (lastReadParam != date && lastReadParam != "0") { // If it was a new day, just add a comma and directly write the data

            // A comma is appended before the data to write
            val d = read(key) + "," + data

            // Write the data in database
            write(key, d)

        } else { // Completely new . No record was found. Just add the data

            // Write the data in database
            write(key, data)
        }

        // Write the LastSaved parameter to database
        write(C.PREF_LASTSAVED, date)
    }

    /**
     * Update Usage counts into database
     * @param t Delay long value in millis
     */
    fun updateUsage(t: Long) {

        val date = Helper().getDateToday()                    // Get today's date
        val lastReadParam = read(C.PREF_LASTSAVED)            // Get last saved date

        if (lastReadParam == date) { // Update for today !

            val d = Solver().stringToList(read(C.PREF_USAGE)).toMutableList()   // Get the list
            val lastRecord = read(C.PREF_USAGE).toFloat()                                   // Last record value
            val timeTemp = read(C.PREF_TIME_TEMP).toLong()                                  // The temp time stored

            if (lastRecord >= 60) {                                                                // Check if the time is more than 60. Obviously, it will be in minutes. SO, write the type as Hour
                write(C.PREF_TIME_TYPE, C.TIME_TYPE_HRS)
            }

            val type = read(C.PREF_TIME_TYPE)                                               // Now re-check the time type
            val timeTotal =                                                                  // Change the time as per the type
                if (type == C.TIME_TYPE_MINS || C.PREF_TIME_TYPE == "0") {
                    Solver().getTime(TimeUnit.MINUTES, timeTemp + t)                  // Its just minutes. Get the value in minutes from the temporary stored data
                } else {
                    Solver().getTime(
                        TimeUnit.HOURS,
                        timeTemp + t
                    )                                                                               // get hours from temporarily stored long timing
                }
            d[d.lastIndex] = timeTotal.toString()                                                   // Last index has our latest data. Update it
            write(C.PREF_USAGE, Solver().listToString(d.toList()))                                  // Write the data back into database
            write(C.PREF_TIME_TEMP, (timeTemp + t).toString())                                      // Add last temp time and new time difference and update the temp time into database
        }

        // New Day !
        else if (lastReadParam != date && lastReadParam != "0") {
            write(C.PREF_TIME_TYPE, C.TIME_TYPE_MINS)                                               // Because it's a new day, timing starts in minutes. Write that type in database
            val d = read(C.PREF_USAGE) + "," + Solver().getTime(TimeUnit.MINUTES, t).toString() // Add the new data into the database
            write(C.PREF_USAGE, d)                                                                  // Write the new value to database
            write(C.PREF_TIME_TEMP, t.toString())                                                   // Write the new temp time to database  - this temp time is our new delay
        }

        // Completely New !
        else {
            write(C.PREF_USAGE, Solver().getTime(TimeUnit.MINUTES, t).toString())                   // It is a new system. Write the data to database directly
            write(C.PREF_TIME_TYPE, C.TIME_TYPE_MINS)                                               // New type always starts in minutes
            write(C.PREF_TIME_TEMP, t.toString())                                                   // Write the new temp time
        }

        // Write the LastSaved parameter to database
        write(C.PREF_LASTSAVED, date)
    }

    /**
     * Updates the age depending on current year and sign-up year. Age needs to be updated every year right?
     */
    fun updateAge() {
        val created = read(C.PROFILE_SIGNUP_YEAR)                         // Get year of sign-up
        val cAge = read(C.PROFILE_SIGNUP_AGE).toInt()                       // Get age given during sign-up
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)         // Get current year using calendar
        val newAge = cAge + (currentYear - created.toInt())                 // Simple Math
        write(C.PROFILE_AGE, newAge.toString())                                  // Update the age
    }

}