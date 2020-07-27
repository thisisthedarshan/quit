/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "ServiceMaker.kt" is a part of the project "Quit"                                           *
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

package com.aztekstudios.quit.handlers

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.aztekstudios.quit.services.MyMonitor
import com.aztekstudios.quit.util.Helper

/**
 * This class creates the Service. This class is designed to be accessible from any other class withing app
 */
class ServiceMaker {

    /**
     * Create the service. Literally, BAKE IT !
     */
    fun bake(c: Context) {
        // Check if service is alive or not
        if (Helper().checkServiceAlive(c, MyMonitor::class.java).not()) {
            // If version is more than 8 , create a foreground service
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(c, Intent(c, MyMonitor::class.java))
            } else {
                // Make a normal service
                c.startService(Intent(c, MyMonitor::class.java))
            }
        }
    }

}