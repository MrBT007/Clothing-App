package com.example.clothingapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    list:ArrayList<Fragment>,
    fm:FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm,lifecycle) {

    private val fragmentList:ArrayList<Fragment> = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}