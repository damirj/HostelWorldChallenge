package com.example.hostelworldchallenge.presenters

import androidx.navigation.NavController
import com.example.hostelworldchallenge.models.PropertyDetails
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.core.Flowable

interface PropertyDetailsContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun displayPropertyDetails(propertyDetails: PropertyDetails)
        fun displayError(message: String)
    }

    interface Model {
        fun fetchPropertyList(): Flowable<PropertyPreviewResponse>
    }

    interface Presenter {
        fun backClicked(navController: NavController)
        fun screenStarted(propertyId: String)
        fun onDestroy()
    }
}