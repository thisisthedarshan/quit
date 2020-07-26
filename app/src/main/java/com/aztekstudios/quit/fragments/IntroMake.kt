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

class IntroMake : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramName: String? = null
    private var paramDescription: String? = null
    private var paramBackgroundColor: Int = Color.parseColor("#00FFFFFF")
    private var paramTextColor: Int = Color.BLACK
    private var paramBackgroundImage: Int? = null
    private var paramImageIntro: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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