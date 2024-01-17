package com.example.clothingapp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.clothingapp.R
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah)

        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                checkLoggedInState()
//                startActivity(Intent(this, WelcomeActivity::class.java))
            },2500)
    }

    private fun checkLoggedInState() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            // User is already logged in, navigate to UserDashboardActivity
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            // User is not logged in, navigate to LoginActivity
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        finish() // Optional: finish the current activity to prevent the user from coming back to it using the back button
    }
}