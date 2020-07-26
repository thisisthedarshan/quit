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

class Profile : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val name = root.findViewById<TextView>(R.id.profileName)
        val userImage = root.findViewById<CircularImageView>(R.id.profilePicture)
        val subtitle = root.findViewById<TextView>(R.id.profileDes)
        val averageUsage = root.findViewById<TextView>(R.id.usageCountProfile)
        val averageUnlocks = root.findViewById<TextView>(R.id.unlockCountProfile)
        //val type =  root.findViewById<TextView>(R.id.profileType)
        val badge = root.findViewById<TextView>(R.id.currentStatusProfile)
        // Obtain class callers
        val prefs = DataManager(requireContext())
        val solver = Solver()
        // Obtain values
        val usageList = solver.StringToList(prefs.read(C.PREF_USAGE))
        val unlocksList = solver.StringToList(prefs.read(C.PREF_UNLOCKS))
        val image = Helper().readImage(C.PROFILE_USER_IMAGE, requireContext())
        // Assignment
        name.text = prefs.read(C.PROFILE_NAME)
        subtitle.text =
            prefs.read(C.PROFILE_AGE) + ", " + prefs.read(C.PROFILE_PROFESSION) // Show age + profession
        //type.text = prefs.read(C.PROFILE_TYPE)
        badge.text = prefs.read(C.PREF_BADGE)
        averageUsage.text = solver.getAverageUsage(usageList).toString()
        averageUnlocks.text = solver.getAverageUsage(unlocksList).toString()
        userImage.setImageBitmap(image)
        return root
    }

}