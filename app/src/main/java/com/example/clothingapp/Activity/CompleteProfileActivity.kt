package com.example.clothingapp.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.cuneytayyildiz.onboarder.utils.color
import com.example.clothingapp.Constant.Constants
import com.example.clothingapp.Firebase.User
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivityCompleteProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.IOException

class CompleteProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompleteProfileBinding
    private var mUserImageUri: Uri? =null
    private val REQUEST_CAMERA = 2
    private val REQUEST_GALLERY = 1
    private var mUser = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCompleteProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val genders = resources.getStringArray(R.array.gender_array)

        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,genders)
        binding.tvAutoComplete.setAdapter(arrayAdapter)

        binding.btnChangePictureCompleteProfile.setOnClickListener {
            showDialog()
        }

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS))
        {
            mUser = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etNameCompleteProfile.setText(mUser.name)
        binding.etNameCompleteProfile.isEnabled = false
        binding.etNameCompleteProfile.setTextColor(color(R.color.custom_grey))

        binding.etEmailCompleteProfile.setText(mUser.email)
        binding.etEmailCompleteProfile.isEnabled = false
        binding.etEmailCompleteProfile.setTextColor(color(R.color.custom_grey))


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    // Gallery Image Pick
                    handleGalleryImage(data)
                }
                REQUEST_CAMERA -> {
                    // Camera Image Capture
                    handleCameraImage(data)
                }
            }
        }
    }

    private fun handleGalleryImage(data: Intent?) {
        if (data != null) {
            try {
                // Check if the selected data is of type image
                val contentResolver = contentResolver
                val mimeType = contentResolver.getType(data.data!!)

                if (mimeType != null && mimeType.startsWith("image/")) {
                    mUserImageUri = data.data!!
                    binding.ivUserImageCompleteProfile.setImageURI(mUserImageUri)
                } else {
                    Toast.makeText(this, "Please select a valid image", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Image Selection Failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleCameraImage(data: Intent?) {
        // The image captured from the camera is already set in the onActivityResult

        val photo: Bitmap? = data?.extras?.get("data") as? Bitmap

        binding.ivUserImageCompleteProfile.setImageBitmap(photo)
    }

    private fun showDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.image_dialog_layout)
        dialog.show()

        val camera = dialog.findViewById<LinearLayout>(R.id.ll_camera)
        val gallery = dialog.findViewById<LinearLayout>(R.id.ll_gallery)

        camera?.setOnClickListener {
            openCamera()
            dialog.dismiss()
        }

        gallery?.setOnClickListener {
            openGallery()
            dialog.dismiss()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA)
        }
    }

    private fun openGallery() {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES)
        startActivityForResult(intent, REQUEST_GALLERY)
    }
}