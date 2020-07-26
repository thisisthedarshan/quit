package com.aztekstudios.quit.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.fragments.IntroMake
import com.aztekstudios.quit.util.Helper
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroPageTransformerType

class Launchpad : AppIntro2() {
    // Fragments are defined here. This is so that we can request permissions. Maybe , can we customize them even more ?
    lateinit var f1: IntroMake
    lateinit var f2: IntroMake
    lateinit var f3: IntroMake
    lateinit var f4: IntroMake
    lateinit var f5: IntroMake

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
            Color.WHITE, Color.BLACK,
            R.drawable.begin_screen, R.drawable.ic_done
        )

        // Set in immersive mode
        setImmersiveMode()
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

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        //startActivity(Intent(this, ProfileMaker::class.java))
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this, ProfileMaker::class.java))
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        when (newFragment) {
            f4 -> requestStatsPermission()
            f5 -> requestOverlayPermission()
        }
    }

    private fun requestStatsPermission() {
        if (Helper().tryGetPermission(this)) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
    }

    private fun requestOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 0)
        }
    }

}