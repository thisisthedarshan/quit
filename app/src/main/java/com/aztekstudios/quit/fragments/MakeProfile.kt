/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "MakeProfile.kt" is a part of the project "Quit"                                            *
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

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.aztekstudios.quit.R
import com.aztekstudios.quit.ui.Home
import com.aztekstudios.quit.util.C
import com.aztekstudios.quit.util.DataManager
import com.aztekstudios.quit.util.Helper
import java.util.*

/**
 * Fragment which handles the work of making the user's profile. It collects data like profession, age, gender (for research purposes only !) and a profile image. <p>
 * All this data is stored locally.
 */
class MakeProfile : Fragment() {
    // Get variables
    private lateinit var age: EditText
    private lateinit var name: TextView
    private lateinit var profession: AutoCompleteTextView
    private lateinit var gender: Spinner
    private var picture: Bitmap? = null
    private lateinit var imageButton: ImageButton
    private lateinit var pref: DataManager

    // Some constant request codes
    private val capture: Int = 16
    private val crop: Int = 61

    // Array of several genders - We are collecting it because in the future, we can use it for research. For now, it's stored locally
    private val genders = arrayOf(
        "Male",
        "Female",
        "Other",
        "Prefer not to say"
    )

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get root
        val root = inflater.inflate(R.layout.fragment_make_profile, container, false)

        // Setup Data Manager
        pref = DataManager(requireContext())

        // Setup views
        name = root.findViewById(R.id.nameProfile)
        profession = root.findViewById(R.id.professionProfile)
        age = root.findViewById(R.id.ageProfile)
        gender = root.findViewById(R.id.genderProfile)
        imageButton = root.findViewById<ImageButton>(R.id.pictureProfile)

        // Set array adapter for gender spinner
        ArrayAdapter(requireContext(), R.layout.spinner_profile, genders).also {
            //it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gender.adapter = it
        }

        // Set ImageButton click listener
        imageButton.setOnClickListener {
            getProfileImage()
        }

        // Set the name value. This is fetched from database where it was stored from Login
        name.text = "Name: ${pref.read(C.PROFILE_NAME)}"

        // Set the save button
        val save = root.findViewById<Button>(R.id.profileSave)
        save.setOnClickListener { buildProfile() }

        // Setup the professions adapter
        val professionsAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.select_dialog_item,
            resources.getStringArray(R.array.professions)
        )
        profession.setAdapter(professionsAdapter)

        // Return the complete view
        return root
    }

    /**
     * This function is executed when save button is pressed. This function handles the task of saving the profile to database
     */
    private fun buildProfile() {
        // Get some variables
        val prof = profession.text.toString()
        val ageNum = age.text.toString()
        val gender = genders[gender.selectedItemPosition]

        // Check if any input section was empty or not
        if (prof.isBlank() || ageNum.isBlank() || gender.isBlank() || picture == null) {
            Toast.makeText(requireContext(), R.string.toast_profile_incomplete, Toast.LENGTH_SHORT)
                .show()
            return // Return if something was missing
        }

        // If everything seems fine, continue writing to database
        pref.write(C.PROFILE_PROFESSION, prof)      // Write the profession
        pref.write(C.PROFILE_SIGNUP_AGE, ageNum)    // Age given during sign-up. This is to dynamically update the age
        pref.write(C.PROFILE_USER_GENDER, gender)   // Gender given during sign-up
        pref.write(C.PROFILE_SIGNUP_YEAR, Calendar.getInstance().get(Calendar.YEAR).toString()) // Write sign-up year
        pref.updateAge()    // Set-up age variable
        pref.write(C.PREF_TIMEOUT, "15")    // Set default timeout. Later, in upcoming updates, we can make it dynamic that is set according to the user's age and profession

        Helper().writeImageFile(picture!!, C.PROFILE_USER_IMAGE, requireContext())  // Write the profile image to internal storage

        activity?.startActivity(Intent(requireContext(), Home::class.java)) // Everything is done, why not start the Home class?

        activity?.finish()  // End of activity
    }

    /**
     * The function to initiate the intent to get a profile picture
     */
    private fun getProfileImage() {
        val getImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(getImage, capture)
    }

    /**
     * This function initiates the crop intent. The final cropped image can be obtained in ActivityResult
     * @param image The Uri of image file received from ACTION_PICK intent
     */
    private fun cropImage(image: Uri) {
        // Crop intent
        val cropIntent = Intent("com.android.camera.action.CROP")   // Action
        cropIntent.setDataAndType(image, "image/*")
        cropIntent.putExtra("crop", "true")                   // Set for crop
        cropIntent.putExtra("aspectX", 1)                     // Set 1x1 ratio for decent image
        cropIntent.putExtra("aspectY", 1)
        cropIntent.putExtra("outputX", 256)                   // Preferred output size
        cropIntent.putExtra("outputY", 256)
        cropIntent.putExtra("return-data", true)              // Set return preference
        startActivityForResult(cropIntent, crop)                           // Start intent
    }

    /**
     * Handle the ActivityResults
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) { // Check if everything was Okay

            when (requestCode) {

                // When requestCode == capture , start the crop intent
                capture -> cropImage(data?.data!!)

                // When requestCode == crop , set the image bitmap
                crop -> {
                    val extra = data?.extras!!           // Get extras from intent
                    picture = extra.getParcelable("data")   // Get bitmap from parcelable
                    imageButton.setImageBitmap(picture)          // Show the selected bitmap on ImageButton
                }

            }
        }
    }
}