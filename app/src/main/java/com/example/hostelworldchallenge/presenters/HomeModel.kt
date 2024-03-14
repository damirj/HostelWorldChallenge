package com.example.hostelworldchallenge.presenters

import com.example.hostelworldchallenge.models.PropertyPreview
import com.example.hostelworldchallenge.network.HostelWorldRepository
import javax.inject.Inject

class HomeModel @Inject constructor(
    private val hostelWorldRepository: HostelWorldRepository
) : HomeContract.Model {

    override fun fetchPropertyList(): List<PropertyPreview>? {
        return hostelWorldRepository.getHostelList()
    }
}