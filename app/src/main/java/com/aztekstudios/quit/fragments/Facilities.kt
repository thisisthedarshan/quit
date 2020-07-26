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

class Facilities : Fragment() {

    lateinit var pref: DataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = DataManager((requireContext()))
        val root = inflater.inflate(R.layout.fragment_facilities, container, false)
        val seekTimeout = root.findViewById<SeekBar>(R.id.seekTimeout)
        val timeCounter = root.findViewById<TextView>(R.id.timeoutCount)
        val timeoutDefault = pref.read(C.PREF_TIMEOUT).toInt()
        seekTimeout.progress = timeoutDefault
        timeCounter.text = timeoutDefault.toString()
        seekTimeout.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var p = 5
                if (progress > 5) {
                    p = progress
                }
                if (progress < 5) {
                    seekBar?.progress = 5
                }
                timeCounter.text = p.toString()
                // set values
                pref.write(C.PREF_TIMEOUT, p.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not much to do here
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not much to do here
            }

        })
        return root
    }

}