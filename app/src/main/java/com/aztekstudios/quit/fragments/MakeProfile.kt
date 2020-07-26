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


class MakeProfile : Fragment() {

    private lateinit var age: EditText
    private lateinit var name: TextView
    private lateinit var profession: AutoCompleteTextView
    private lateinit var gender: Spinner
    private var picture: Bitmap? = null
    private lateinit var imageButton: ImageButton
    private lateinit var pref: DataManager

    private val capture: Int = 16
    private val crop: Int = 61

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
        val root = inflater.inflate(R.layout.fragment_make_profile, container, false)
        pref = DataManager(requireContext())
        name = root.findViewById(R.id.nameProfile)
        profession = root.findViewById(R.id.professionProfile)
        age = root.findViewById(R.id.ageProfile)
        gender = root.findViewById(R.id.genderProfile)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genders).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gender.adapter = it
        }
        imageButton = root.findViewById<ImageButton>(R.id.pictureProfile)
        imageButton.setOnClickListener {
            captureImage()
        }
        name.text = "Name: ${pref.read(C.PROFILE_NAME)}"
        val save = root.findViewById<Button>(R.id.profileSave)
        save.setOnClickListener { buildProfile() }
        val professionsAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.select_dialog_item,
            resources.getStringArray(R.array.professions)
        )
        profession.setAdapter(professionsAdapter)
        return root
    }

    fun buildProfile() {
        val prof = profession.text.toString()
        val ageNum = age.text.toString()
        val gender = genders[gender.selectedItemPosition]
        if (prof.isBlank() || ageNum.isBlank() || gender.isBlank() || picture == null) {
            Toast.makeText(requireContext(), R.string.toast_profile_incomplete, Toast.LENGTH_SHORT)
                .show()
            return
        }
        pref.write(C.PROFILE_PROFESSION, prof)
        pref.write(C.PROFILE_SIGNUP_AGE, ageNum)
        pref.write(C.PROFILE_USER_GENDER, gender)
        pref.write(C.PROFILE_SIGNUP_YEAR, Calendar.getInstance().get(Calendar.YEAR).toString())
        pref.updateAge()
        Helper().writeImageFile(picture!!, C.PROFILE_USER_IMAGE, requireContext())
        pref.write(C.PREF_TIMEOUT, "15")
        activity?.startActivity(Intent(requireContext(), Home::class.java))
        activity?.finish()
    }

    fun captureImage() {
        val getImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(getImage, capture)
    }

    fun cropImage(image: Uri) {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(image, "image/*")
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra("aspectX", 1)
        cropIntent.putExtra("aspectY", 1)
        cropIntent.putExtra("outputX", 256)
        cropIntent.putExtra("outputY", 256)
        cropIntent.putExtra("return-data", true)
        startActivityForResult(cropIntent, crop)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                capture -> cropImage(data?.data!!)
                crop -> {
                    val extra = data?.extras!!
                    picture = extra.getParcelable("data")
                    imageButton.setImageBitmap(picture)
                }
            }
        }
    }
}