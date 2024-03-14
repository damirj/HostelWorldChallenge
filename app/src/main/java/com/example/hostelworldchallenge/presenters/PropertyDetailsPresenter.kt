package com.example.hostelworldchallenge.presenters

import androidx.navigation.NavController
import com.example.hostelworldchallenge.models.CurrencyType
import com.example.hostelworldchallenge.models.PropertyDetails
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PropertyDetailsPresenter(
    private var propertyDetailsView: PropertyDetailsContract.View?,
    private val propertyDetailsModel: PropertyDetailsContract.Model
) : PropertyDetailsContract.Presenter {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun backClicked(navController: NavController) {
        navController.popBackStack()
    }

    override fun screenStarted(propertyId: String) {
        var lowestPrice = 0.0
        if (propertyDetailsView != null) {
            propertyDetailsView?.showProgress()
            val fetchPropertyList =
                propertyDetailsModel.fetchPropertyList().toObservable().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            val getExchangeRate = fetchPropertyList.flatMap {
                val parsedPropertyResponse = parsePropertyResponse(
                    propertyPreviewResponse = it,
                    propertyId = propertyId
                )
                lowestPrice = parsedPropertyResponse.lowestPricePerNight.toDouble()
                propertyDetailsView?.displayPropertyDetails(parsedPropertyResponse)

                propertyDetailsModel.getExchangeRates().toObservable().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }

            compositeDisposable.add(
                getExchangeRate.doFinally { propertyDetailsView?.hideProgress() }
                    .subscribe({
                        propertyDetailsView?.displayExchangeRate(lowestPrice, it)
                    }, {
                        propertyDetailsView?.displayError(it.localizedMessage ?: "Something went wrong. Please try again!")
                    })
            )
        }
    }

    override fun onDestroy() {
        propertyDetailsView = null
        compositeDisposable.clear()
    }

    private fun parsePropertyResponse(
        propertyPreviewResponse: PropertyPreviewResponse,
        propertyId: String
    ): PropertyDetails {
        val propertyData =
            propertyPreviewResponse.properties.first { it.id.toString() == propertyId }

        return PropertyDetails(
            id = propertyData.id.toString(),
            previewImage = "https://" + propertyData.imagesGallery.first().prefix + propertyData.imagesGallery.first().suffix,
            propertyName = propertyData.name,
            ratingNumber = propertyData.overallRating.overall.toDouble().div(10),
            numberOfRatings = propertyData.overallRating.numberOfRatings.toInt(),
            propertyType = propertyData.type.lowercase()
                .replaceFirstChar { firstChar -> firstChar.uppercase() },
            lowestPricePerNight = if ((propertyData.lowestDormPricePerNight?.value?.toDouble()
                    ?: 0.0) < (propertyData.lowestPrivatePricePerNight?.value?.toDouble() ?: 0.0)
            ) propertyData.lowestDormPricePerNight?.value
                ?: "0" else propertyData.lowestPrivatePricePerNight?.value
                ?: "0",
            overview = propertyData.overview,
            ratingBreakdown = propertyData.ratingBreakdown,
            address = propertyData.address1 + ", " + propertyData.address2,
            city = propertyPreviewResponse.location.city.name,
            currency = CurrencyType.EUR
        )
    }

}