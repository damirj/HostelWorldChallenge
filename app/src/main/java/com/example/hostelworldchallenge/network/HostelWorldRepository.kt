package com.example.hostelworldchallenge.network

import com.example.hostelworldchallenge.models.CurrencyType
import com.example.hostelworldchallenge.models.PropertyPreview
import javax.inject.Inject

class HostelWorldRepository @Inject constructor(
    private val hostelWorldService: HostelWorldService
) {

    suspend fun getHostelList(): List<PropertyPreview>? {
        val propertyListResponse = hostelWorldService.getHostelList()
        if (!propertyListResponse.isSuccessful) return null
        val propertyData = propertyListResponse.body()?.properties?.map {
            PropertyPreview(
                previewImage = "https://" + it.imagesGallery.first().prefix + it.imagesGallery.first().suffix,
                propertyName = it.name,
                ratingNumber = it.overallRating.overall.toDouble().div(10),
                numberOfRatings = it.overallRating.numberOfRatings.toInt(),
                propertyType = it.type.lowercase()
                    .replaceFirstChar { firstChar -> firstChar.uppercase() },
                propertyDistance = it.distance.value,
                freeWifi = it.facilities.first().facilities.any { facility -> facility.id == "FREEWIFI" || facility.id == "FREEINTERNETACCESS" },
                freeBreakfast = it.facilities.first().facilities.any { facility -> facility.id == "BREAKFASTINCLUDED" },
                freeCancellation = it.freeCancellationAvailable,
                featuredProperty = it.isFeatured,
                lowestDormPricePerNight = it.lowestDormPricePerNight?.value ?: "",
                lowestPrivatePricePerNight = it.lowestPrivatePricePerNight?.value
                    ?: "",
                currency = CurrencyType.EUR
            )
        }

        return propertyData
    }
}