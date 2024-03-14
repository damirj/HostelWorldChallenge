package com.example.hostelworldchallenge.network

import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface HostelWorldService {

    @GET("a1517b9da90dd6877385a65f324ffbc3/raw/058e83aa802907cb0032a15ca9054da563138541/properties.json")
    fun getHostelList(): Flowable<PropertyPreviewResponse>
}