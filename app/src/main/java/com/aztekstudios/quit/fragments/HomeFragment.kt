/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "HomeFragment.kt" is a part of the project "Quit"                                           *
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
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.viewmodels.HomeViewModel

/**
 * The Home screen of the application. This view is shown by default. <p>
 * It shows various things like number of unlocks and usage time
 */
class HomeFragment : Fragment() {

    // Home View Model Instance
    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the root
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Get relevant views
        val unHolder = root.findViewById<TextView>(R.id.unlockTime)
        val usHolder = root.findViewById<TextView>(R.id.usageTime)
        val unTxt = root.findViewById<TextView>(R.id.unlockTimeOutOf)
        val usTxt = root.findViewById<TextView>(R.id.usageTimeOutOf)

        // Initialise storage
        val pref = DataManager(requireContext())

        // Obtain data from the storage
        var timeType = pref.read(C.PREF_TIME_TYPE)
        var unlocksMax = pref.read(C.PREF_UNLOCKS_MAX)

        // Set Unlocks and Time Type
        when {
            timeType == "0" -> timeType = C.TIME_TYPE_MINS
            unlocksMax == "0" -> unlocksMax = "50"
        }

        // Display the text
        unTxt.text = "out of $unlocksMax"
        usTxt.text = timeType

        // Obtain ViewModel for displaying data
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        // Update the data
        homeViewModel.updateStuff(requireContext())
        // Display the received data
        homeViewModel.usage.observe(viewLifecycleOwner) {
            usHolder.text = it
        }
        homeViewModel.unlocks.observe(viewLifecycleOwner) {
            unHolder.text = it
        }

        // Return the root
        return root
    }

}