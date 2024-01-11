package com.example.clothingapp.Activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding:ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCreateNewPasswordFromForgotPassword.setOnClickListener {
            if(emailIDValidated()){
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        val email = binding.etEmailFromForgotPassword.text.toString()

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Email sent.")
                    Toast.makeText(this, "Email Sent Successfully", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    showErrorSnackBar("Failed to send password reset email." + task.exception?.message, true)
                }
            }
    }

    private fun emailIDValidated(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmailFromForgotPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmailFromForgotPassword.text.toString().trim { it <= ' ' }).matches() -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_invalid_email), true)
                false
            }
            else ->{
                true
            }
        }
    }
}