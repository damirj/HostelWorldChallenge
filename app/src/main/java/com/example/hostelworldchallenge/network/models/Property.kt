package com.example.hostelworldchallenge.network.models

data class Property(
    val address1: String,
    val address2: String,
    val distance: Distance,
    val facilities: List<Facility>,
    val freeCancellationAvailable: Boolean,
    val id: Int,
    val imagesGallery: List<ImagesGallery>,
    val isFeatured: Boolean,
    val lowestDormPricePerNight: LowestDormPricePerNight?,
    val lowestPrivatePricePerNight: LowestPrivatePricePerNight?,
    val name: String,
    val overallRating: OverallRating,
    val overview: String,
    val ratingBreakdown: RatingBreakdown,
    val type: String,
)