package com.example.hostelworldchallenge.presenters

import com.example.hostelworldchallenge.models.PropertyPreview
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable

interface HomeContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun displayPropertyList(propertyList: List<PropertyPreview>)
        fun displayHeaderInfo(city: String, numberOfProperties: Int)
        fun displayError(message: String)
    }

    interface Model {
        fun fetchPropertyList(): Flowable<PropertyPreviewResponse>
    }

    interface Presenter {
        fun screenStarted()
        fun onDestroy()
    }
}