package com.example.hostelworldchallenge.presenters

class HomePresenter(
    private var homeView: HomeContract.View?,
    private val homeModel: HomeContract.Model
) : HomeContract.Presenter {

    override fun screenStarted() {
        if (homeView != null) {
            homeView?.showProgress()
            val result = homeModel.fetchPropertyList()
            if (!result.isNullOrEmpty()) {
                homeView?.displayPropertyList(result)
            } else {
                homeView?.displayError("Something went wrong. Try again, please.")
            }
            homeView?.hideProgress()
        }
    }

    override fun onDestroy() {
        homeView = null
    }
}