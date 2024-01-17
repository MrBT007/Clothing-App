package com.example.clothingapp.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.cuneytayyildiz.onboarder.utils.color
import com.example.clothingapp.Constant.Constants
import com.example.clothingapp.Firebase.FirestoreClass
import com.example.clothingapp.Firebase.User
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivityCompleteProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.io.IOException
import java.util.concurrent.TimeUnit

class CompleteProfileActivity : BaseActivity(), OTPVerificationDialog.OTPVerificationListener,
    OTPVerificationDialog.resendOtpListener {
    private lateinit var binding: ActivityCompleteProfileBinding
    private var mUserImageUri: Uri? =null
    private val REQUEST_CAMERA = 2
    private val REQUEST_GALLERY = 1
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth

    private lateinit var otpVerificationDialog: OTPVerificationDialog
    private var mUser = User()
    private var mUserProfileImageURL: String ?= null
    val userHashMap = HashMap<String,Any>()

    //TODO : Change to false after testing mode
    private var isNumberVerified = true

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCompleteProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val genders = resources.getStringArray(R.array.gender_array)

        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item,genders)
        binding.genderSelector.setAdapter(arrayAdapter)

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

        binding.btnCompleteFromCompleteProfile.setOnClickListener {
            if(detailsAreValid()){
                if(mUserImageUri!=null)
                {
                    FirestoreClass().uploadImageToCloudStorage(this,mUserImageUri,Constants.USER_PROFILE_IMAGE)

                    if(mUserProfileImageURL!=null)
                    {
                        userHashMap[Constants.PHOTO] = mUserProfileImageURL!!
                    }
                }

                updateUserProfileDetails()
            }
        }

        binding.btnVerifyMobileNumber.setOnClickListener {

            if (binding.etMobileNumberCompleteProfile.text!!.length == 10) {
                sendOtp()
                showProgressDialog("Please wait...")
            } else {
                showErrorSnackBar("Enter Valid Number", true)
            }
        }
    }

    private fun updateUserProfileDetails() {
        val name = binding.etNameCompleteProfile.text.toString().trim{ it <= ' '}
        if(name != mUser.name)
        {
            userHashMap[Constants.NAME] = name
        }

        val countryCode = binding.countryCodeCompleteProfile.selectedCountryCode()
        val phoneNo = binding.etMobileNumberCompleteProfile.text.toString()
        val mobileNumber = countryCode+phoneNo
        val gender = binding.genderSelector.text.toString()

        if(mUserProfileImageURL!=null)
        {
            userHashMap[Constants.PHOTO] = mUserProfileImageURL!!
        }
        else{
            userHashMap[Constants.PHOTO]  = ""
        }

        userHashMap[Constants.PHONENO] = mobileNumber
        userHashMap[Constants.GENDER] = gender
        userHashMap[Constants.COMPLETE_PROFILE] = true

        // these fields remains same
        userHashMap[Constants.EMAIL] = mUser.email
        userHashMap[Constants.ID] = mUser.id

//        showProgressDialog(resources.getString(R.string.please_wait))
        Log.e("UserMap", "updateUserProfileDetails: $userHashMap", )
        FirestoreClass().updateUserDetails(this, userHashMap)
    }

    private fun detailsAreValid(): Boolean {
        return when{
            binding.etMobileNumberCompleteProfile.length()!=10 ->{
                showErrorSnackBar("Mobile Number is not valid",true)
                false
            }
            //Todo: Add statement to check if mobile number is already in use
            binding.genderSelector.text.toString() == "Select Gender" ->{
                showErrorSnackBar("Gender is not selected",true)
                false
            }
            !isNumberVerified ->{
                showErrorSnackBar("Mobile number is not verified",true)
                false
            }
            else -> {
                true
            }
        }
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



    override fun resendOtp() {
        sendOtp()
    }

    override fun onOTPVerified() {
        // Perform actions after OTP verification
        otpVerificationDialog.dismiss()
        binding.btnVerifyMobileNumber.text = "Verified"
        binding.btnVerifyMobileNumber.setTextColor(color(R.color.colorSnackBarSuccess))
        binding.etMobileNumberCompleteProfile.isEnabled = false
        binding.countryCodeCompleteProfile.isEnabled = false
        isNumberVerified = true
        showErrorSnackBar("Mobile number is verified", false)
    }

    override fun sendOtp() {

        var countryCode = binding.countryCodeCompleteProfile.selectedCountryCode()
        var mobileNumber = binding.etMobileNumberCompleteProfile.text.toString()

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //credential is verified in OTPVerification Dialog.
            }

            override fun onVerificationFailed(e: FirebaseException) {
//                Log.e("Mobile number", "onVerificationFailed: ${countryCode+mobileNumber}")
                hideProgressDialog()
                Log.e("OTP", "onVerificationFailed: ", e)
                showErrorSnackBar("${e.localizedMessage}", true)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                hideProgressDialog()
                otpVerificationDialog = OTPVerificationDialog(
                    this@CompleteProfileActivity,
                    countryCode,
                    mobileNumber,
                    verificationId,
                    this@CompleteProfileActivity
                )
                otpVerificationDialog.setOTPVerificationListener(this@CompleteProfileActivity)
                otpVerificationDialog.setResendOtpListener(this@CompleteProfileActivity)
                otpVerificationDialog.setCancelable(false)
                otpVerificationDialog.show()
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(countryCode + mobileNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallback) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun imageUploadSuccess(imageRL:String)
    {
//        hideProgressDialog()
        mUserProfileImageURL = imageRL

        // as in any case we have to update the user details although we haven't selected a profile image.
        updateUserProfileDetails()
        Log.e("imageUploadSuccess: ", mUserProfileImageURL!!)
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,DashboardActivity::class.java))
    }

}