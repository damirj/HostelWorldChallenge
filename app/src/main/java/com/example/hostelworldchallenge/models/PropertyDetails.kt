package com.example.hostelworldchallenge.models

import com.example.hostelworldchallenge.network.models.RatingBreakdown

data class PropertyDetails(
    val id: String,
    val previewImage: String,
    val propertyName: String,
    val ratingNumber: Double,
    val numberOfRatings: Int,
    val propertyType: String,
    val lowestPricePerNight: String,
    val overview: String,
    val ratingBreakdown: RatingBreakdown,
    val address: String,
    val city: String,
    val currency: CurrencyType
) {
    fun getRatingName(): String {
        return when {
            ratingNumber < 6 -> {
                ""
            }

            ratingNumber >= 6 && ratingNumber < 7 -> {
                "Good"
            }

            ratingNumber >= 7 && ratingNumber < 8 -> {
                "Very good"
            }

            ratingNumber >= 8 && ratingNumber < 9 -> {
                "Fabulous"
            }

            ratingNumber in 9.0..10.0 -> {
                "Superb"
            }

            else -> {
                ""
            }
        }
    }
}