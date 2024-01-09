package com.example.clothingapp.Activity

import android.content.Intent
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cuneytayyildiz.onboarder.OnboarderActivity
import com.cuneytayyildiz.onboarder.model.OnboarderPage
import com.cuneytayyildiz.onboarder.model.description
import com.cuneytayyildiz.onboarder.model.image
import com.cuneytayyildiz.onboarder.model.onboarderPage
import com.cuneytayyildiz.onboarder.model.title
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener
import com.cuneytayyildiz.onboarder.utils.color
import com.example.clothingapp.R

class OnboardingActivity: OnboarderActivity(),OnboarderPageChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnboarderPageChangeListener(this)

        shouldUseFloatingActionButton(true)

        setActiveIndicatorColor(R.color.custom_brown)
        val pages:MutableList<OnboarderPage> = createOnboardPages()

        initOnboardingPages(pages)
    }


    private fun createOnboardPages(): MutableList<OnboarderPage> {
        return mutableListOf(
            onboarderPage {
                backgroundColor = color(R.color.white)
                image {
                    imageResId = R.drawable.obs2
                }
                title {
                    text = "Find Anything\nYou Need"
                    textColor = R.color.custom_brown
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium
                }
                description {
                    text = "Lorem ipsum get ksie lsodm\nlsdko ldjso londf ladsfnia"
                    textColor = R.color.custom_black
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium

                }

            },
            onboarderPage {
                backgroundColor = color(R.color.white)
                image {
                    imageResId = R.drawable.obs3
                }
                title {
                    text = "Order Now"
                    textColor = R.color.custom_brown
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium
                }
                description {
                    text = "Lorem ipsum get ksie lsodm\nlsdko ldjso londf ladsfnia"
                    textColor = R.color.custom_black
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium

                }
            },
            onboarderPage {
                backgroundColor = color(R.color.white)
                image {
                    imageResId = R.drawable.obs1
                }
                title {
                    text = "Fast\nDelivery"
                    textColor = R.color.custom_black
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium
                }
                description {
                    text = "Lorem ipsum get ksie lsodm\nlsdko ldjso londf ladsfnia"
                    textColor = R.color.custom_black
                    multilineCentered = true
                    typefaceFontResId = R.font.heebo_medium

                }
            }
        )
    }

    override fun onFinishButtonPressed() {
        startActivity(Intent(this,SignInActivity::class.java))
    }

    override fun onPageChanged(position: Int) {
        Log.d(javaClass.simpleName, "onPageChanged: $position")
    }

    override fun onSkipButtonPressed() {
        Toast.makeText(this, "Skipped", Toast.LENGTH_SHORT).show()
    }

}