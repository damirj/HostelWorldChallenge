package com.example.hostelworldchallenge.network.models

data class PropertyPreviewResponse(
    val location: Location,
    val properties: List<Property>
)