package com.example.hostelworldchallenge.presenters

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.hostelworldchallenge.R
import com.example.hostelworldchallenge.models.CurrencyType
import com.example.hostelworldchallenge.models.PropertyPreview
import com.example.hostelworldchallenge.network.models.PropertyPreviewResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(
    private var homeView: HomeContract.View?,
    private val homeModel: HomeContract.Model
) : HomeContract.Presenter {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun screenStarted() {
        if (homeView != null) {
            homeView?.showProgress()
            compositeDisposable.add(homeModel.fetchPropertyList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    homeView?.hideProgress()
                }.subscribe({
                    homeView?.displayHeaderInfo(
                        city = it.location.city.name,
                        numberOfProperties = it.properties.size
                    )
                    homeView?.displayPropertyList(parsePropertyResponse(it))
                }, {
                    homeView?.displayError("Something went wrong. Please try again!")
                })
            )
        }
    }

    override fun onDestroy() {
        homeView = null
        compositeDisposable.clear()
    }

    override fun propertyClicked(navController: NavController, propertyId: String) {
        navController.navigate(
            R.id.propertyDetailsFragment,
            bundleOf(
                "propertyId" to propertyId,
            )
        )
    }

    private fun parsePropertyResponse(propertyPreviewResponse: PropertyPreviewResponse): List<PropertyPreview> {
        val propertyData = propertyPreviewResponse.properties.map {
            PropertyPreview(
                id = it.id.toString(),
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