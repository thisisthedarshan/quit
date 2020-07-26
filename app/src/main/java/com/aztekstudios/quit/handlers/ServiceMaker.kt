package com.aztekstudios.quit.handlers

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.aztekstudios.quit.services.MyMonitor
import com.aztekstudios.quit.util.Helper

class ServiceMaker {
    fun makeService(c: Context) {
        if (Helper().checkServiceAlive(c, MyMonitor::class.java).not()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(c, Intent(c, MyMonitor::class.java))
            } else {
                c.startService(Intent(c, MyMonitor::class.java))
            }
        }
    }

}