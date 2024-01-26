package com.example.clothingapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.clothingapp.R
import com.example.clothingapp.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : FragmentActivity() {
    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_dashboard) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView_user_dashboard)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onBackPressed() {

        if(navController.currentDestination?.id!=R.id.homeFragment)
        {
            navController.popBackStack(R.id.homeFragment,false)
            bottomNavigationView.menu.findItem(R.id.homeFragment).isChecked = true
        }
        else
        {
            if(doubleBackToExitPressedOnce)
            {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true

            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({doubleBackToExitPressedOnce = false},2000)
        }

    }
}