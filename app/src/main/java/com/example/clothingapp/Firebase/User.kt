package com.example.clothingapp.Firebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String="",
    val email:String="",
    val name:String="",
    val gender:String="",
    val phoneNo:String="",
    val photo:String="",
    val role:String="",
    val profileCompleted:Boolean=false,
):Parcelable
