/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Share.kt" is a part of the project "Quit"                                                  *
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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R

/**
 * The Sharing fragment. It also includes option to rate
 */
class Share : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create root
        val root = inflater.inflate(R.layout.fragment_share, container, false)

        // Get relevant views
        val share = root.findViewById<Button>(R.id.share_button)
        val rate = root.findViewById<TextView>(R.id.share_support_message)

        // Set their functions
        share.setOnClickListener {
            // Sharing Intent
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareMessage =
                ("Hey, check out this cool app. It helped me fight mobile addiction. It could help you too.\nHere's the link to Project Quit.\nhttps://github.com/thisisthedarshan/quit . " +
                        "The app is available on Play store. Download it here \uD83D\uDC49 https://play.google.com/store/apps/details?com.aztekstudios.quit").trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        rate.setOnClickListener {
            // Rating intent. This just opens the browser or store fot providing rating
            val rateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?com.aztekstudios.quit"))
            startActivity(rateIntent)
        }

        // Return view
        return root
    }

}