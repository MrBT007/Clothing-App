package com.example.clothingapp.Constant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap


object Constants
{
    const val ROLE = "role"
    const val COMPLETE_PROFILE = "profileCompleted"
    const val GENDER = "gender"
    const val PHONENO = "phoneNo"
    const val NAME = "name"
    const val RB_PREFERENCES = "RBPrefs"
    const val USERS = "users"
    const val LOGGED_IN_USERNAME = "logged_in_username"
    const val EXTRA_USER_DETAILS = "extra_user_details"

    const val USER_PROFILE_IMAGE = "User_profile_image"


    const val PHOTO = "photo"
    const val MALE = "Male"
    const val FEMALE = "Female"
    const val PASSWORD = "password"
    const val ID = "id"
    const val EMAIL = "email"

    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1
    fun getFileExtension(activity: Activity,uri: Uri?): String?
    {
        /*
        MimeTypeMap:Two-way map that maps MIME-types to file extensions and vice versa.

        getSingleton():Get the singleton instance of MimeTypeMap.

        getExtensionFromMimeType:Return the registered extension for the given MIME type.

        contentResolver.getType:Return the MIME type of the given content URL.
        */

        //c:/Tushar Bhut/download/user.jpg  --> Uri
        // this will return the .jpg
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}