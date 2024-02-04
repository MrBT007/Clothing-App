package com.example.clothingapp.Fragment.User

import CardPagerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.clothingapp.Adapter.ClothCategoryAdapter
import com.example.clothingapp.Data.CardData
import com.example.clothingapp.Data.ClothCategoryData
import com.example.clothingapp.R
import com.example.clothingapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardList = listOf(
            CardData("Title 1", "Description 1", R.drawable.fashion1),
            CardData("Title 2", "Description 2", R.drawable.fashion2),
            CardData("Title 3", "Description 3", R.drawable.fashion3),
            // Add more card data as needed
        )

        //Todo:Data should be fetched from the firestore
        val initialCategories = listOf(
            ClothCategoryData(R.drawable.tshirt, "T-Shirt"),
            ClothCategoryData(R.drawable.pant, "Pant"),
            ClothCategoryData(R.drawable.dress, "Dress"),
            ClothCategoryData(R.drawable.jacket, "Jacket"),
            ClothCategoryData(R.drawable.shirt, "Shirt"),
            ClothCategoryData(R.drawable.hoodie, "Hoodie"),
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = ClothCategoryAdapter(requireContext(),initialCategories)

        val cardAdapter = CardPagerAdapter(cardList)
        binding.viewPager.adapter = cardAdapter
        binding.springDotsIndicator.attachTo(binding.viewPager)
    }

}