package com.example.hostelworldchallenge.presenters

import com.example.hostelworldchallenge.models.PropertyPreview

interface HomeContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun displayPropertyList(propertyList: List<PropertyPreview>)
        fun displayError(message: String)
    }

    interface Model {
        fun fetchPropertyList(): List<PropertyPreview>?
    }

    interface Presenter {
        fun screenStarted()
        fun onDestroy()
    }
}