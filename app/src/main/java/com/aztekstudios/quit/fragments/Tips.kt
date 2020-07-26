package com.aztekstudios.quit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.QuoteFactory
import com.aztekstudios.quit.util.Solver

class Tips : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tips, container, false)
        val quoteTextView = root.findViewById<TextView>(R.id.quote)
        val authorTextView = root.findViewById<TextView>(R.id.author)
        // Generate Quote
        val quote = QuoteFactory().getRandomQuote()
        quoteTextView.text = Solver().justGetTheQuote(quote)
        authorTextView.text = Solver().getAuthorFromQuote(quote)
        return root
    }
}