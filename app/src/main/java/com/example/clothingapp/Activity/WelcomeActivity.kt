package com.example.clothingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLetsGetStartedWelcome.setOnClickListener{
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        binding.btnSignInWelcome.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }

    override fun onBackPressed() {
        doubleBackToExit()
    }
}