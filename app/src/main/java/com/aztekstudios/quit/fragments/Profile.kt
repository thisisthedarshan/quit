/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Profile.kt" is a part of the project "Quit"                                                *
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
import com.aztekstudios.quit.R
import com.aztekstudios.quit.models.CircularImageView
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.Helper
import com.aztekstudios.quit.util.Solver

/**
 *  This fragment loads local data and shows the user's their profile.
 */
class Profile : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        // Get relevant views
        val name = root.findViewById<TextView>(R.id.profileName)
        val userImage = root.findViewById<CircularImageView>(R.id.profilePicture)
        val subtitle = root.findViewById<TextView>(R.id.profileDes)
        val averageUsage = root.findViewById<TextView>(R.id.usageCountProfile)
        val averageUnlocks = root.findViewById<TextView>(R.id.unlockCountProfile)
        val badge = root.findViewById<TextView>(R.id.currentStatusProfile)
        //val type =  root.findViewById<TextView>(R.id.profileType) // Future Implementation

        // Obtain callers
        val prefs = DataManager(requireContext())
        val solver = Solver()

        // Obtain values
        val usageList = solver.stringToList(prefs.read(C.PREF_USAGE))
        val unlocksList = solver.stringToList(prefs.read(C.PREF_UNLOCKS))
        val image = Helper().readImage(C.PROFILE_USER_IMAGE, requireContext())

        // Assignment
        name.text = prefs.read(C.PROFILE_NAME)
        subtitle.text =
            prefs.read(C.PROFILE_AGE) + ", " + prefs.read(C.PROFILE_PROFESSION) // Show age + profession
        badge.text = prefs.read(C.PREF_BADGE)
        averageUsage.text = solver.getAverageUsage(usageList).toString()        // Average Usage
        averageUnlocks.text = solver.getAverageUnlocks(unlocksList).toString()  // Average Unlocks
        userImage.setImageBitmap(image)
        //type.text = prefs.read(C.PROFILE_TYPE)

        // Return built UI
        return root
    }

}