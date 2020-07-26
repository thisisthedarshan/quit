package com.aztekstudios.quit.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aztekstudios.quit.R
import com.aztekstudios.quit.handlers.ServiceMaker
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.Helper
import com.google.android.material.navigation.NavigationView


class Home : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Load simple profile
        val image = Helper().readImage(C.PROFILE_USER_IMAGE, this)
        var name = DataManager(this).read(C.PROFILE_NAME)
        if (name == "0") name = getString(R.string.nav_header_title)
        // Setup layout
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController: NavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon)
        navController.addOnDestinationChangedListener { _, des, _ ->
            if (des.id == R.id.nav_home) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon)
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon_arrow)
            }
            navView.setCheckedItem(des.id)
        }
        // Set the display contents
        val header = navView.getHeaderView(0)//The default header which we set
        header.findViewById<TextView>(R.id.username).text = name
        header.findViewById<ImageView>(R.id.profilePicture).setImageBitmap(image)
        // Permissions matter !
        if (Helper().tryGetPermission(this)) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        }
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 0)
        }
        // Run Service
        ServiceMaker().makeService(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}