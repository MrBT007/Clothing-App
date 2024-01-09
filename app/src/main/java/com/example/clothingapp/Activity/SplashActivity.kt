package com.example.clothingapp.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.clothingapp.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                startActivity(Intent(this, WelcomeActivity::class.java))
            },2500)
    }
}