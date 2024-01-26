package com.example.clothingapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.clothingapp.Activity.SignInActivity
import com.example.clothingapp.Firebase.FirestoreClass
import com.example.clothingapp.Firebase.User
import com.example.clothingapp.R
import com.example.clothingapp.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

private var mUser: User? = null
class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        showProgressDialog("Please Wait...")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Inflate the layout for this fragment

        // Display user data if it has been loaded

        if (mUser == null) {
            Log.i("Load Data", "onCreateView: Load 1st time")
            loadUserDetails()
        }

        binding.btnSignOutProfileFragment.setOnClickListener {
            showConfirmationDialog()
        }
        return root
    }

    private fun showConfirmationDialog() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.logout_dialog_layout)
        dialog.show()

        val cancel = dialog.findViewById<MaterialButton>(R.id.btn_logout_cancel)
        val logout = dialog.findViewById<MaterialButton>(R.id.btn_logout_yes)

        cancel?.setOnClickListener {
            dialog.dismiss()
        }

        logout?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }
    }

    private fun loadUserDetails() {
        FirestoreClass().getUserDetailsFragment(this) { user ->
            mUser = user
            userDetailsSuccess(user)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load user data only if it hasn't been loaded before
        mUser?.let { userDetailsSuccess(it) }
    }
    private fun userDetailsSuccess(user: User) {
        Glide.with(this).load(user.photo).into(binding.ivUserImageCompleteProfile)
        binding.nameProfileFragment.text=user.name
        hideProgressDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}