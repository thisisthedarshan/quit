package com.aztekstudios.quit.fragments

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

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val unHolder = root.findViewById<TextView>(R.id.unlockTime)
        val usHolder = root.findViewById<TextView>(R.id.usageTime)
        val unTxt = root.findViewById<TextView>(R.id.unlockTimeOutOf)
        val usTxt = root.findViewById<TextView>(R.id.usageTimeOutOf)

        val pref = DataManager(requireContext())
        var timeType = pref.read(C.PREF_TIME_TYPE)
        var unlocksMax = pref.read(C.PREF_UNLOCKS_MAX)
        when {
            timeType == "0" -> timeType = C.TIME_TYPE_MINS
            unlocksMax == "0" -> unlocksMax = "50"
        }

        unTxt.text = "out of $unlocksMax"
        usTxt.text = timeType

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.updateStuff(requireContext())
        homeViewModel.usage.observe(viewLifecycleOwner) {
            usHolder.text = it
        }
        homeViewModel.unlocks.observe(viewLifecycleOwner) {
            unHolder.text = it
        }
        return root
    }

}