/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "About.kt" is a part of the project "Quit"                                                  *
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

package com.aztekstudios.quit.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.BuildConfig
import com.aztekstudios.quit.R

/**
 * Fragment to display the About section for the app.
 */
class About : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the root view
        val root = inflater.inflate(R.layout.fragment_about, container, false)

        // Setup the views
        val gitButton = root.findViewById<ImageButton>(R.id.gitButton)
        val mailButton = root.findViewById<ImageButton>(R.id.mailButton)
        val versionTxt = root.findViewById<TextView>(R.id.versionCode)

        // Change the version displayed on view
        versionTxt.text = "Project Quit, version " + BuildConfig.VERSION_NAME

        // Add option to open github page for this project
        gitButton.setOnClickListener {
            // GO TO This project's GitHub Page
            val gitIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thisisthedarshan/quit"))
            startActivity(gitIntent)
        }

        // Option to send mail
        mailButton.setOnClickListener {
            // Mail me via email
            val mailIntent = Intent(Intent.ACTION_SEND).also {
                it.data = Uri.parse("mailto:")
                it.type = "text/plain"
                it.putExtra(Intent.EXTRA_EMAIL, "studios.aztek@gmail.com")
                it.putExtra(Intent.EXTRA_SUBJECT, "Query regarding Project quit")
                it.putExtra(Intent.EXTRA_TEXT, "Write your message here")
            }
            startActivity(Intent.createChooser(mailIntent, "Send Mail via"))
        }

        // Return the root view after creating the fragment
        return root
    }
}