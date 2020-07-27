/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Home.kt" is a part of the project "Quit"                                                   *
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

/**
 * The Home Activity in the application
 */
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
        if (name == "0") name = getString(R.string.nav_header_title)    // Default Title XD

        // Setup layout
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController: NavController = findNavController(R.id.nav_host_fragment)

        // Navigation Configuration for Drawer
        appBarConfiguration = AppBarConfiguration(
            navController.graph, drawerLayout
        )

        // Setup
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon)

        // Dynamically changing the icon of the navigation menu. This is just done for a better UI feel
        navController.addOnDestinationChangedListener { _, des, _ ->
            if (des.id == R.id.nav_home) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon)           // Regular 3 Line icon
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer_icon_arrow)     // Icon that looks like back arrow
            }
            navView.setCheckedItem(des.id)   // Automatic highlighting probably had some because we are using custom destination changed listener so this line.
        }

        // Set the display contents
        val header = navView.getHeaderView(0)//The default header which we set
        header.findViewById<TextView>(R.id.username).text = name
        header.findViewById<ImageView>(R.id.profilePicture).setImageBitmap(image)

        // Permissions matter !
        if (Helper().tryGetPermission(this)) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)  // Permission for App Usage Statistics
            startActivity(intent)
        }
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,               // Permission for Overlay
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 0)
        }

        // Run Service
        ServiceMaker().bake(this)
    }

    /**
     * Manage Navigation and fragment transactions with navigation controller
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)           // Navigation controller of the host fragment
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()  // Set the navigation
    }


}