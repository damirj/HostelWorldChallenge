package com.example.hostelworldchallenge.models

data class PropertyPreview(
    val previewImage: String,
    val propertyName: String,
    val ratingNumber: Double,
    val numberOfRatings: Int,
    val propertyType: String,
    val propertyDistance: Double,
    val freeWifi: Boolean,
    val freeBreakfast: Boolean,
    val freeCancellation: Boolean,
    val featuredProperty: Boolean,
    val lowestDormPricePerNight: String,
    val lowestPrivatePricePerNight: String,
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

enum class CurrencyType(val symbol: String) {
    EUR("€"), USD("$"), GBP("£")
}