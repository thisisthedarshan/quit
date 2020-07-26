package com.aztekstudios.quit.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.Solver
import com.aztekstudios.quit.workers.ServiceInitWork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.concurrent.TimeUnit


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        // Check if service is active or not
        // Start the job
        startJob()
        // Start the badger function to update the badge XD
        Solver().badger(this)
        // Here we go to the home
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
     * Initialise the work - This starts the Services periodically.
     */
    fun startJob() {
        val request = PeriodicWorkRequestBuilder<ServiceInitWork>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(C.UNIQUE_JOB_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
    }
}