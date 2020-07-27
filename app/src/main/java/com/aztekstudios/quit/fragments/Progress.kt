/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Progress.kt" is a part of the project "Quit"                                               *
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
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.Helper
import com.aztekstudios.quit.viewmodels.ProgressViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData

/**
 * The fragment which shows the user's progress. It includes the graphs for unlocks and usage
 */
class Progress : Fragment() {

    // Variable
    lateinit var progressVM: ProgressViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set the ViewModel
        progressVM = ViewModelProviders.of(this).get(ProgressViewModel::class.java)

        // Get the root
        val root = inflater.inflate(R.layout.fragment_progress, container, false)
        val graph1 = root.findViewById<BarChart>(R.id.graphUsage)
        val graph2 = root.findViewById<BarChart>(R.id.graphUnlocked)

        // Get BarDataSet for graphs
        val gData1 = progressVM.usageBarData(requireContext())
        val gData2 = progressVM.unlocksBarData(requireContext())

        // Create data sets
        val dSet1 = BarData(gData1)
        val dSet2 = BarData(gData2)

        // Modify them
        dSet1.barWidth = 0.9f
        dSet2.barWidth = 0.9f

        // Assign to charts and set parameters
        graph1.data = dSet1
        graph2.data = dSet2
        graph1.setFitBars(true)
        graph2.setFitBars(true)
        graph1.axisLeft.axisMinimum = 0f
        graph1.axisRight.axisMinimum = 0f
        graph2.axisLeft.axisMinimum = 0f
        graph2.axisRight.axisMinimum = 0f

        // Set Descriptions
        graph1.description.text = getString(R.string.description_usage)
        graph2.description.text = getString(R.string.description_unlocks)

        // Invalidate - Draw on canvas
        graph1.invalidate()
        graph2.invalidate()

        // Set badge
        val badge = root.findViewById<TextView>(R.id.progress_type)
        badge.text = "Progress = ${Helper().getBadge(requireContext())}"

        // Return view
        return root
    }
}