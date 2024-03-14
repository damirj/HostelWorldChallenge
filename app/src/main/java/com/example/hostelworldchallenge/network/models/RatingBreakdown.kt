package com.example.hostelworldchallenge.network.models

data class RatingBreakdown(
    val clean: Int,
    val facilities: Int,
    val location: Int,
    val security: Int,
    val staff: Int,
    val value: Int
)