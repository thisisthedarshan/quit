package com.aztekstudios.quit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Hey, check out this cool app. It helped me fight mobile addiction. It could help you too."
            )
            val shareMessage =
                "\nHere's the link to Project Quit app.\nhttps://github.com/thisisthedarshan/quit ".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        return root
    }

}