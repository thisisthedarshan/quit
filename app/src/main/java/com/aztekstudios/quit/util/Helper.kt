/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Helper.kt" is a part of the project "Quit"                                                 *
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

import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import com.aztekstudios.quit.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * Class with helpful functions for the program.
 */
class Helper {

    /**
     * Function that returns the last saved date from database
     * @param c Context of the caller
     * @return The Date in form of string
     */
    fun getLastDate(c: Context): String {
        return DataManager(c).read(C.PREF_LASTSAVED)
    }

    /**
     * Function to obtain today's date in a string pattern
     * @return Date in a ddMMyyyy pattern. Example 21112012 would be returned for 21 November(11) 2012
     */
    fun getDateToday(): String {
        return SimpleDateFormat(
            "ddMMMyyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
    }

    /**
     * Get application name from given package name
     * @param c Context of the caller - Needed for PackageManager
     * @param packageName Package Name of the application whose name is to be obtained
     * @return Application Name corresponding to the given Package Name
     */
    fun getAppName(c: Context, packageName: String): String {
        var name: String
        with(c.packageManager) {
            name = getApplicationLabel(
                getApplicationInfo(
                    packageName,
                    PackageManager.GET_META_DATA
                )
            ).toString()
        }
        return name
    }

    /**
     * Retrieves the latest usage statistics data from memory
     * @param c Context of the caller
     * @return Formatted string representing latest usage from memory
     */
    fun getTodayUsage(c: Context): String {
        return if (getDateToday() == getLastDate(c)) {                   // If the request date is equal to last-written parameter, do the following
            val prefRead = DataManager(c).read(C.PREF_USAGE)      // Read usage from memory
            val lst =
                Solver().stringToList(prefRead)                          // Last one is the latest one. Load it into list
            val lastItem = lst.last()
                .toFloat()                                               // Store the value into a variable so that we can format it
            String.format(
                "%.2f",
                lastItem
            )                                                            // I think it was good formatting it using string format option. So this does it and returns just the data rounded to 2 decimal points
        } else {
            "0.0"                                                        // If last written date doesn't match today's date, return 0
        }
    }

    /**
     * Retrieves the latest unlock counts from database
     * @param c Context of the caller
     * @return The integer in form of a string representing latest unlock count
     */
    fun getTodayUnlocks(c: Context): String {
        return if (getDateToday() == getLastDate(c)) {                   // If the request date is equal to last-written parameter, do the following
            val prefRead = DataManager(c).read(C.PREF_UNLOCKS)    // Read usage from memory
            val lst =
                Solver().stringToList(prefRead)                          // Last one is the latest one. Load it into list
            lst.last()                                                   // Return the last value. This holds the latest value
        } else {
            "0"                                                          // If last written date doesn't match today's date, return 0
        }
    }

    /**
     * Retrieves the user's badge from memory
     * @param c Context of caller
     * @return The badge as a string
     */
    fun getBadge(c: Context): String {
        return when (val pref = DataManager(c).read(C.PREF_BADGE)) {
            "0" -> {
                C.BADGE_1                                                // No value was stored. Just return the beginners badge
            }
            else -> {
                pref                                                     // We have something. Just return that
            }
        }
    }

    /**
     * Checks if a service is active or not. This function uses Activity Manager to retrieve the status
     * @param c Context of caller
     * @param serviceClass Class of the service to validate with
     * @return Returns a boolean value corresponding to the status of the service
     */
    fun checkServiceAlive(c: Context, serviceClass: Class<*>): Boolean {
        val manager =
            c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager? // ActivityManager instance
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {           // Iterate through the list of retrieved info
                return true                                                 // Return true if the service is amongst that list
            }
        }
        return false                                                        // Return false if we didn't find the service into the list. This means the service was not running
    }

    /**
     * Generates a random integer color value
     * @return Random Color Integer value
     */
    fun generateRandomColor(): Int {
        val r = Random() // Random function instance
        return Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)) // Get random colors XD
    }

    /**
     * This function writes an image file to memory
     * @param bmp Bitmap file to be written
     * @param title Title of the file without extension. Default extension is PNG
     * @param c Context
     */
    fun writeImageFile(bmp: Bitmap, title: String, c: Context) {
        try {
            val dir: File = c.getDir("images", Context.MODE_PRIVATE)    // Open a directory
            val imageFile = File(dir, "$title.png")                      // Make a file
            if (imageFile.exists()) {                                          // Check if file exists
                imageFile.delete()                                             // Delete if exists
            }
            imageFile.createNewFile()                                          // Create a new file
            val fOut = FileOutputStream(imageFile)                             // Open a file output stream to write image
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut)          // Compress the bitmap into PNG and write it out with the output stream
            fOut.flush()                                                        // Flush the stream
            fOut.close()                                                        // Work done. Close it.
        } catch (e: Exception) {
            // StillToDO?
        }
    }

    /**
     * This function reads an image file from memory
     * @param title Title of the file to read. This should be without extension. Default one is set to PNG
     * @param c Context
     * @return Bitmap of the image file read from memory
     */
    fun readImage(title: String, c: Context): Bitmap {
        val dir: File = c.getDir("images", Context.MODE_PRIVATE)       // Open a directory
        val f = File(dir, "$title.png")                                 // Make a file
        return try {
            BitmapFactory.decodeStream(FileInputStream(f))                    // Open a input stream from file and decode the stream to a bitmap
        } catch (e: java.lang.Exception) {
            BitmapFactory.decodeResource(c.resources, R.drawable.pikachu)     // Error. Return the default image from drawables
        }
    }

    /**
     * Checks if the application was granted the permission to read Application Statistics.
     * @param c Context
     * @return True if permission granted, else False
     */
    fun tryGetPermission(c: Context): Boolean {
        return try {
            val packageManager: PackageManager = c.packageManager               // Package Manager
            val applicationInfo =
                packageManager.getApplicationInfo(c.packageName, 0)       // Get application info for this app
            val appOpsManager =
                c.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager    // AppOpsManager instance
            val mode = appOpsManager.checkOpNoThrow(                       // Check for support
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                applicationInfo.uid,
                applicationInfo.packageName
            )
            mode != AppOpsManager.MODE_ALLOWED                                  // Return
        } catch (e: PackageManager.NameNotFoundException) {
            false                                                               // Error. Return false
        }
    }
}