/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "SplashScreen.kt" is a part of the project "Quit"                                           *
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

package com.aztekstudios.quit.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aztekstudios.quit.R
import com.aztekstudios.quit.broadcasts.BootReceiver
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.Solver
import com.aztekstudios.quit.workers.ServiceInitWork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.concurrent.TimeUnit

/**
 * The splash screen activity of the application
 */
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Check if service is active or not
        // Start the job
        startJob()

        // Add the intent filter to register Boot Receiver
        val intentBoot = IntentFilter(Intent.ACTION_BOOT_COMPLETED)
        registerReceiver(BootReceiver(),intentBoot)

        // Start the badger function to update the badge XD
        Solver().badger(this)

        // Check if the user is logged in. If not, start the intro or just go to home.
        Handler().postDelayed({
            // Check if logged-in
            val account = GoogleSignIn.getLastSignedInAccount(this)
            if (account != null) {
                startActivity(Intent(this, Home::class.java))
            } else {
                startActivity(Intent(this, Launchpad::class.java))
            }
            finish()
        }, 1800)
    }

    /**
     * Initialise the work - This ensures that the Services restarts if it had died earlier.
     */
    private fun startJob() {
        val request = PeriodicWorkRequestBuilder<ServiceInitWork>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(C.UNIQUE_JOB_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
    }
}