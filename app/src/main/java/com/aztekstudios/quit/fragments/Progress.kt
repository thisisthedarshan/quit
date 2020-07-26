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

class Progress : Fragment() {
    lateinit var progressVM: ProgressViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        progressVM = ViewModelProviders.of(this).get(ProgressViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_progress, container, false)
        val graph1 = root.findViewById<BarChart>(R.id.graphUsage)
        val graph2 = root.findViewById<BarChart>(R.id.graphUnlocked)
        val gData1 = progressVM.usageBarData(requireContext())
        val gData2 = progressVM.unlocksBarData(requireContext())
        // Create data sets
        val dSet1 = BarData(gData1)
        val dSet2 = BarData(gData2)
        // Modify them
        dSet1.barWidth = 0.9f        // Display needs to be bold !
        dSet2.barWidth = 0.9f        // Display needs to be bold !
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
        return root
    }
}