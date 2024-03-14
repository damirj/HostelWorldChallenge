package com.example.hostelworldchallenge.presenters

import com.example.hostelworldchallenge.network.HostelWorldRepository
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class HomeModel @Inject constructor(
    private val hostelWorldRepository: HostelWorldRepository
) : HomeContract.Model {

    override fun fetchPropertyList(): Flowable<PropertyPreviewResponse> {
        return hostelWorldRepository.getHostelList()
    }
}