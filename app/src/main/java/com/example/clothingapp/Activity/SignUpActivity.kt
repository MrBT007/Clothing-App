package com.example.clothingapp.Activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.clothingapp.Firebase.FirestoreClass
import com.example.clothingapp.Firebase.User
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignInSignup.setOnClickListener{
            onBackPressed()
        }

        binding.btnSignupFromSignup.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        if(signUpDetailsValidated()){
            showProgressDialog("Please wait...")
            registerUser()
        }
    }

    private fun registerUser() {
        val name = binding.etNameFromSignup.text.toString().trim { it <= ' ' }
        val email = binding.etEmailFromSignup.text.toString().trim { it <= ' ' }
        val password = binding.etPasswordFromSignup.text.toString().trim { it <= ' ' }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val firebaseUser = task.result.user
                    val user = User(
                        firebaseUser!!.uid,
                        email,name,"","","","user",false
                    )

                    FirestoreClass().registerUser(this,user)
                }
                else{
                    hideProgressDialog()
                    showErrorSnackBar(task.exception?.message.toString(),true)
                }
            }
    }

    private fun signUpDetailsValidated(): Boolean {
        return when{
            TextUtils.isEmpty(binding.etNameFromSignup.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(binding.etEmailFromSignup.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmailFromSignup.text.toString().trim { it <= ' ' }).matches() -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_invalid_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPasswordFromSignup.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            binding.etPasswordFromSignup.text.toString().trim { it <= ' ' }.length < 6 -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_length), true)
                false
            }
            TextUtils.isEmpty(binding.etConfirmPasswordFromSignup.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }
            binding.etPasswordFromSignup.text.toString().trim { it <= ' ' } != binding.etConfirmPasswordFromSignup.text.toString().trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            else -> true
        }
    }

    fun userRegisteredSuccessfully() {
        hideProgressDialog()
        Toast.makeText(this, "You are Registered Successfully", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }
}