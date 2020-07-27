/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "Tips.kt" is a part of the project "Quit"                                                   *
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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.util.QuoteFactory
import com.aztekstudios.quit.util.Solver

/**
 *  This fragment creates the view to display tips and quotes.
 */
class Tips : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate root
        val root = inflater.inflate(R.layout.fragment_tips, container, false)

        // Get relevant views
        val quoteTextView = root.findViewById<TextView>(R.id.quote)
        val authorTextView = root.findViewById<TextView>(R.id.author)

        // Generate Quote
        val quote = QuoteFactory().getRandomQuote()

        // Set views
        quoteTextView.text = Solver().getTheQuote(quote)
        authorTextView.text = Solver().getAuthorFromQuote(quote)

        // Return root
        return root
    }
}