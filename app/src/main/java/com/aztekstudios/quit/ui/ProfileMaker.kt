package com.aztekstudios.quit.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.aztekstudios.quit.R
import com.aztekstudios.quit.fragments.Login

class ProfileMaker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_maker)
        // Request Storage permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 12)
        }
        try {
            supportFragmentManager.commit {
                replace(R.id.containerProfileMaker, Login())
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error creating login fragment ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}