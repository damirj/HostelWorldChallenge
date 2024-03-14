package com.example.hostelworldchallenge.network

import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class HostelWorldRepository @Inject constructor(
    private val hostelWorldService: HostelWorldService
) {

    fun getHostelList(): Flowable<PropertyPreviewResponse> {
        return hostelWorldService.getHostelList()
    }
}