package com.aztekstudios.quit.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R

class Share : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        val share = root.findViewById<Button>(R.id.share_button)
        share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareMessage =
                ("Hey, check out this cool app. It helped me fight mobile addiction. It could help you too.\nHere's the link to Project Quit.\nhttps://github.com/thisisthedarshan/quit . " +
                        "The app is available on Play store. Download it here \uD83D\uDC49 https://play.google.com/store/apps/details?com.aztekstudios.quit").trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        val rate = root.findViewById<TextView>(R.id.share_support_message)
        rate.setOnClickListener {
            val rateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?com.aztekstudios.quit"))
            startActivity(rateIntent)
        }
        return root
    }

}