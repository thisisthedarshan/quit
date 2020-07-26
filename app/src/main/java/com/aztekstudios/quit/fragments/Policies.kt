package com.aztekstudios.quit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R

class Policies : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_policies, container, false)
        val view1 = root.findViewById<WebView>(R.id.privacyPolicy)
        val view2 = root.findViewById<WebView>(R.id.termsAndConditions)
        view1.loadUrl("file:///android_asset/privacypolicy.html")
        view2.loadUrl("file:///android_asset/tncs.html")
        return root
    }

}