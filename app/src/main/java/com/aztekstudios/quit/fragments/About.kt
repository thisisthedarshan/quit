package com.aztekstudios.quit.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.BuildConfig
import com.aztekstudios.quit.R


class About : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val gitButton = root.findViewById<ImageButton>(R.id.gitButton)
        val mailButton = root.findViewById<ImageButton>(R.id.mailButton)
        val versionTxt = root.findViewById<TextView>(R.id.versionCode)
        versionTxt.text = "Project Quit, version " + BuildConfig.VERSION_NAME
        gitButton.setOnClickListener {
            // GO TO This project's GitHub Page
            val gitIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thisisthedarshan/quit"))
            startActivity(gitIntent)
        }
        mailButton.setOnClickListener {
            // Mail me via email
            val mailIntent = Intent(Intent.ACTION_SEND).also {
                it.data = Uri.parse("mailto:")
                it.type = "text/plain"
                it.putExtra(Intent.EXTRA_EMAIL, "studios.aztek@gmail.com")
                it.putExtra(Intent.EXTRA_SUBJECT, "Query regarding Project quit")
                it.putExtra(Intent.EXTRA_TEXT, "Write your message here")
            }
            startActivity(Intent.createChooser(mailIntent, "Send Mail via"))
        }
        return root
    }
}