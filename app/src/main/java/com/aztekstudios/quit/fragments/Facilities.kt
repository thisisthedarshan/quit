/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Facilities.kt" is a part of the project "Quit"                                             *
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
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager

/**
 * The fragment which shows various features of the application.
 */
class Facilities : Fragment() {

    // Data handling class
    lateinit var pref: DataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = DataManager((requireContext())) // Provide context
        // Get root view
        val root = inflater.inflate(R.layout.fragment_facilities, container, false)

        // Find other relevant views
        val seekTimeout = root.findViewById<SeekBar>(R.id.seekTimeout)
        val timeCounter = root.findViewById<TextView>(R.id.timeoutCount)
        val timeoutDefault = pref.read(C.PREF_TIMEOUT).toInt()

        // Set properties
        seekTimeout.progress = timeoutDefault
        timeCounter.text = timeoutDefault.toString()

        // Seek-bar change listener. This is to change the timeout duration to show pop-ups
        seekTimeout.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var p = 5 // Set the minimum timeout
                if (progress > 5) {
                    p = progress
                }
                if (progress < 5) {
                    seekBar?.progress = 5
                }
                timeCounter.text = p.toString() // Show which time the user has selected
                // set values
                pref.write(C.PREF_TIMEOUT, p.toString()) // Write it down in memory
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not much to do here
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not much to do here
            }

        })

        //return the root view
        return root
    }

}