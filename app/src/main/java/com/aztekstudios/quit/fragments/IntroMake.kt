/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "IntroMake.kt" is a part of the project "Quit"                                              *
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

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val title = "title_intro" // Title
private const val description = "description_intro" // Description of the intro fragment
private const val backgroundColor =
    "color_background_intro" // Background of the text color, default = transparent
private const val textColor = "text_color_intro" // Color of the text, default = Black
private const val backgroundImage = "image_background_intro" // Background image
private const val introImage = "image_info_intro" // The image to show in intro, not it's background

/**
 * Class to make the view fragment for showing introduction.
 */
class IntroMake : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramName: String? = null           // Title
    private var paramDescription: String? = null    // Description
    private var paramBackgroundColor: Int = Color.parseColor("#00FFFFFF")   // Background color
    private var paramTextColor: Int = Color.BLACK   // Text Color
    private var paramBackgroundImage: Int? = null   // Background Image
    private var paramImageIntro: Int? = null        // Just another Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtain arguments
        arguments?.let {
            paramName = it.getString(title)
            paramDescription = it.getString(description)
            paramBackgroundColor = it.getInt(backgroundColor)
            paramTextColor = it.getInt(textColor)
            paramBackgroundImage = it.getInt(backgroundImage)
            paramImageIntro = it.getInt(introImage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Start making the fragment
        val root = inflater.inflate(R.layout.fragment_intromake, container, false)

        // Obtain views
        val titleTV = root.findViewById<TextView>(R.id.introTitle)
        val desTV = root.findViewById<TextView>(R.id.introDescription)
        val introImg = root.findViewById<ImageView>(R.id.introImage)
        val introBcgImg = root.findViewById<ImageView>(R.id.introBackground)

        // Put colors first
        titleTV.setBackgroundColor(paramBackgroundColor)
        desTV.setBackgroundColor(paramBackgroundColor)
        titleTV.setTextColor(paramTextColor)
        desTV.setTextColor(paramTextColor)

        // Then assign the images
        introImg.setImageResource(paramImageIntro!!)
        introBcgImg.setImageResource(paramBackgroundImage!!)

        // Finally, set texts
        titleTV.text = paramName
        desTV.text = paramDescription

        // Start making the fragment
        return root
    }

    companion object {
        /**
         * Use this method to create a new instance of
         * intro fragment using the provided parameters
         * like title, background image, and other parameters.
         * @param name The title of the view
         * @param des The description to show
         * @param backgroundCol Background Color as a Color Resource
         * @param textCol Color of the text
         * @param backgroundImg Image to show as background
         * @param introImg The image shown above description
         * @return IntroMake Fragment with the given parameters
         *
         */
        @JvmStatic
        fun newInstance(
            name: String,
            des: String,
            @ColorInt backgroundCol: Int,
            @ColorInt textCol: Int,
            @DrawableRes backgroundImg: Int,
            @DrawableRes introImg: Int
        ) =
            // Set the bundle and get the fragment
            IntroMake().apply {
                arguments = Bundle().apply {
                    putString(title, name)
                    putString(description, des)
                    putInt(backgroundColor, backgroundCol)
                    putInt(textColor, textCol)
                    putInt(backgroundImage, backgroundImg)
                    putInt(introImage, introImg)
                }
            }
    }
}