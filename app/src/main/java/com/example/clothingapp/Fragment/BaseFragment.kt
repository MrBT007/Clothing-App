package com.example.clothingapp.Fragment

import android.app.Dialog
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.clothingapp.R
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    private var doubleBackToExitPressedOnce = false
    private lateinit var mProgressDialog: Dialog

    fun showErrorSnackBar(message: String, error: Boolean) {
        view?.let {
            val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view

            if(error) {
                snackbarView.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorSnackBarError
                    )
                )
            } else {
                // Show success snackbar
            }

            snackbar.show()
        }
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireContext())

        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    fun doubleBackToExit() {
        if(doubleBackToExitPressedOnce) {
            requireActivity().onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true

        Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}