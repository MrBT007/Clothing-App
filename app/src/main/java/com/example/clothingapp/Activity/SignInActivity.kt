package com.example.clothingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.example.clothingapp.Constant.Constants
import com.example.clothingapp.Firebase.FirestoreClass
import com.example.clothingapp.Firebase.User
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignupFromLogin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnLoginFromLogin.setOnClickListener {
            validateAndLoginUser()
        }

        binding.btnForgotPasswordFromLogin.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
    }

    private fun validateAndLoginUser() {
        if(loginDetailsValidated()){
            showProgressDialog("Please wait...")
            loginUser()
        }
    }

    private fun loginDetailsValidated(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etEmailFromLogin.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmailFromLogin.text.toString().trim { it <= ' ' }).matches() -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_invalid_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPasswordFromLogin.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginUser() {
        val email = binding.etEmailFromLogin.text.toString().trim{it <= ' '}
        val password = binding.etPasswordFromLogin.text.toString().trim{it <= ' '}

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task->
                if(task.isSuccessful)
                {
                    FirestoreClass().getUserDetails(this)
                }
                else
                {
                    hideProgressDialog()
                    if(!Constants.isNetworkConnected(this))
                    {
                        showErrorSnackBar("Sorry, unable to login. Please check your internet connection.", true)
                    }
                }
            }
            .addOnFailureListener { e->
                hideProgressDialog()
                if(!Constants.isNetworkConnected(this))
                {
                    showErrorSnackBar("Sorry, unable to login. Please check your internet connection.", true)
                }
                else
                {
                    showErrorSnackBar("Incorrect Email ID or Password", true)
                }
            }
    }

    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()
        Log.i("First Name:",user.name)
        Log.i("Email:",user.email)

        Log.i("com.example.clothingapp.Firebase.UserManager", "User saved: ${user.name}")
//        Toast.makeText(this, "Congrats ${user.name} you are logged in", Toast.LENGTH_SHORT).show()
        if(!user.profileCompleted)
        {
            val intent = Intent(this, CompleteProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }
        else {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        finish()
    }

}