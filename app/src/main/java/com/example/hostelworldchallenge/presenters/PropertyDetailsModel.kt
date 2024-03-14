package com.example.hostelworldchallenge.presenters

import com.example.hostelworldchallenge.network.HostelWorldRepository
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class PropertyDetailsModel @Inject constructor(
    private val hostelWorldRepository: HostelWorldRepository
) : PropertyDetailsContract.Model {

    override fun fetchPropertyList(): Flowable<PropertyPreviewResponse> {
        // Ideally I would only fetch that specific property using propertyId.
        // Since I don't have a endpoint for that I will fetch all properties and filter the list.
        // Passing the whole Property object from one fragment to another one is considered an anti-pattern.
        return hostelWorldRepository.getHostelList()
    }
}