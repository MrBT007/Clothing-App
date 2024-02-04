package com.example.clothingapp.Firebase

class Product(
    val name: String,
    val price: Double,
    val category:String,
    val colours: List<String>,
    val sizes: Map<String, Int>, // "XS" to 1, "S" to 0, etc., where 1 indicates available and 0 indicates unavailable
    val productDetails: String,
    val images: List<String>, // List of image URLs
    val mainImage: String, // URL of the main display image
    val userReviews: MutableList<Review> = mutableListOf(),
) {
    val rating: Double
        get() = if (userReviews.isNotEmpty()) userReviews.map { it.rating }.average() else 0.0

    fun addReview(review: Review) {
        userReviews.add(review)
    }
}