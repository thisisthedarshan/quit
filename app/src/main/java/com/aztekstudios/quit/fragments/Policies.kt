/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Policies.kt" is a part of the project "Quit"                                               *
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R

/**
 * This fragment shows the Policies related to the application.<p>
 *     It uses simple WebView and displays the HTML files of the related policy
 */
class Policies : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the root
        val root = inflater.inflate(R.layout.fragment_policies, container, false)

        // Get the WebViews
        val view1 = root.findViewById<WebView>(R.id.privacyPolicy)
        val view2 = root.findViewById<WebView>(R.id.termsAndConditions)

        // Load the local files into the WebViews. These files are present in assets folder
        view1.loadUrl("file:///android_asset/privacypolicy.html")
        view2.loadUrl("file:///android_asset/tncs.html")

        // Return root
        return root
    }

}