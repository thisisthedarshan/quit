/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Launchpad.kt" is a part of the project "Quit"                                              *
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
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.fragments.IntroMake
import com.aztekstudios.quit.util.Helper
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroPageTransformerType

/**
 * Class that shows the introduction activity. This class extends AppIntro2 class for showing custom fragments.
 * Read more at the library's wiki on github
 */
class Launchpad : AppIntro2() {

    // Fragment variables are defined here. This is so that we can request permissions. Maybe , can we customize them even more ?
    private lateinit var f1: IntroMake
    private lateinit var f2: IntroMake
    private lateinit var f3: IntroMake
    private lateinit var f4: IntroMake
    private lateinit var f5: IntroMake

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define the fragments here
        f1 = IntroMake.newInstance(
            "Welcome",
            "This is Project QUIT. A way to help you improve ;)",
            Color.WHITE, Color.BLACK,
            R.drawable.go_out_screen, R.mipmap.ic_launcher
        )
        f2 = IntroMake.newInstance(
            "There is a way out",
            "If you have the courage, you can overcome any hurdle. \uD83D\uDE01",
            Color.WHITE, Color.BLACK,
            R.drawable.enjoy_out_screen, R.drawable.ic_human
        )
        f3 = IntroMake.newInstance(
            "Permission to record usage statistics",
            "We need to know what are you using. Data is stored on device only!\nPromise",
            Color.WHITE, Color.BLACK,
            R.drawable.trust_screen, R.drawable.ic_permissions
        )
        f4 = IntroMake.newInstance(
            "Permission to draw over other apps",
            "We need to show you alerts. Nothing more than that!\n",
            Color.WHITE, Color.BLACK,
            R.drawable.trust_screen, R.drawable.ic_permissions
        )
        f5 = IntroMake.newInstance(
            "\uD83C\uDF89",
            "Let's begin our fabulous journey !",
            Color.TRANSPARENT, Color.BLACK,
            R.drawable.begin_screen, R.drawable.ic_done
        )

        // Set in immersive mode
        //setImmersiveMode() // Blocking UI

        // Fade the screens!!!!!!
        setTransformer(AppIntroPageTransformerType.Fade)

        // Some other settings
        isSystemBackButtonLocked = true
        showStatusBar(false)

        // First Intro slide - About slide
        addSlide(f1)

        // Second Intro slide - App info slide
        addSlide(f2)

        // Third Intro slide - Permission slide 1 for PACKAGE_USAGE_STATS permission
        addSlide(f3)

        // Fourth Intro slide - Permission slide 2 for SYSTEM_ALERT_WINDOW permission
        addSlide(f4)

        // Final Login info slide
        addSlide(f5)

    }

    /**
     * Handle when Skip button is pressed. This usually takes to the login segment
     */
    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        fin()
        //startActivity(Intent(this, Home::class.java))
    }

    /**
     * Handle when Done button is pressed. This usually finishes the activity and takes to the login segment
     */
    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        fin()
    }

    /**
     * Handle the transaction of fragment views. This method is triggered when the fragments are changed
     */
    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        when (newFragment) {
            f4 -> requestStatsPermission()
            f5 -> requestOverlayPermission()
        }
    }

    /**
     * Handle Request Permission for reading application statistics
     */
    private fun requestStatsPermission() {
        if (Helper().tryGetPermission(this)) {
            Toast.makeText(applicationContext, R.string.toast_grant_permission, Toast.LENGTH_LONG)
                .show()
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivityForResult(intent,1)
        }
    }

    /**
     * Handle Request Permission for displaying overlays. This is needed to show the alert dialogs via service
     */
    private fun requestOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(applicationContext, R.string.toast_grant_permission, Toast.LENGTH_LONG)
                .show()
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 0)
        }
    }

    /**
     * Starts the ProfileMaker class and closes the activity.
     * This task was repeating so just make a function to handle it
     */
    private fun fin() {
        startActivity(Intent(this, ProfileMaker::class.java))
        finish()
    }


}