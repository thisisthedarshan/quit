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
 * Class with helpful functions
 */
class Helper {

    fun getLastDate(c: Context): String {
        return DataManager(c).read(C.PREF_LASTSAVED)
    }

    fun getDateToday(): String {
        return SimpleDateFormat(
            "ddMMMyyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().time)
    }

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

    fun getTodayUsage(c: Context): String {
        return if (getDateToday() == getLastDate(c)) {
            val prefRead = DataManager(c).read(C.PREF_USAGE)
            val lst = Solver().StringToList(prefRead)
            val lastItem = lst.last().toFloat()
            String.format("%.2f", lastItem)
        } else {
            "0"
        }
    }

    fun getTodayUnlocks(c: Context): String {
        return if (getDateToday() == getLastDate(c)) {
            val prefRead = DataManager(c).read(C.PREF_UNLOCKS)
            val lst = Solver().StringToList(prefRead)
            val ret = lst.last().toFloat()
            ret.toInt().toString()
        } else {
            "0"
        }
    }

    fun getBadge(c: Context): String {
        val pref = DataManager(c).read(C.PREF_BADGE)
        return when (pref) {
            "0" -> {
                C.BADGE_1
            }
            else -> {
                pref
            }
        }
    }

    fun checkServiceAlive(c: Context, serviceClass: Class<*>): Boolean {
        val manager =
            c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun generateRandomColor(): Int {
        val r = Random()
        return Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256))
    }

    /**
     * This function writes an image file to memory with a custom extension.
     */
    fun writeImageFile(bmp: Bitmap, title: String, c: Context) {
        try {
            val dir: File = c.getDir("images", Context.MODE_PRIVATE)
            val imageFile = File(dir, "$title.png")
            if (imageFile.exists()) {
                imageFile.delete()
            }
            imageFile.createNewFile()
            val fOut = FileOutputStream(imageFile)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            // StillToDO?
        }
    }

    /**
     * This function reads an image file to memory with a custom extension.
     */
    fun readImage(title: String, c: Context): Bitmap {
        val dir: File = c.getDir("images", Context.MODE_PRIVATE)
        val f = File(dir, "$title.png")
        return try {
            // val options = BitmapFactory.Options()
            // options.inPreferredConfig = Bitmap.Config.ARGB_8888
            // BitmapFactory.decodeFile(f.absolutePath, options)
            BitmapFactory.decodeStream(FileInputStream(f))
        } catch (e: java.lang.Exception) {
            BitmapFactory.decodeResource(c.resources, R.drawable.pikachu)
        }
    }

    fun tryGetPermission(c: Context): Boolean {
        return try {
            val packageManager: PackageManager = c.packageManager
            val applicationInfo =
                packageManager.getApplicationInfo(c.packageName, 0)
            val appOpsManager =
                c.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOpsManager.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                applicationInfo.uid,
                applicationInfo.packageName
            )
            mode != AppOpsManager.MODE_ALLOWED
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}