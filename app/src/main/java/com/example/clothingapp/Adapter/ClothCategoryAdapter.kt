package com.example.clothingapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothingapp.Data.ClothCategoryData
import com.example.clothingapp.R

class ClothCategoryAdapter(
    private val context: Context,
    private val categories: List<ClothCategoryData>
) : RecyclerView.Adapter<ClothCategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_clothing_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.iconImageView.setImageResource(category.iconResId)
        holder.categoryNameTextView.text = category.categoryName
    }

    override fun getItemCount(): Int = categories.size
}
