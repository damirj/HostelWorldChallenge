package com.example.hostelworldchallenge.network.models

data class RatingBreakdown(
    val average: Int,
    val clean: Int,
    val facilities: Int,
    val `fun`: Int,
    val location: Int,
    val ratingsCount: Int,
    val security: Int,
    val staff: Int,
    val value: Int
)