package com.example.hostelworldchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hostelworldchallenge.R
import com.example.hostelworldchallenge.databinding.FragmentPropertyDetailsBinding
import com.example.hostelworldchallenge.models.PropertyDetails
import com.example.hostelworldchallenge.presenters.PropertyDetailsContract
import com.example.hostelworldchallenge.presenters.PropertyDetailsModel
import com.example.hostelworldchallenge.presenters.PropertyDetailsPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PropertyDetailsFragment : Fragment(), PropertyDetailsContract.View {

    @Inject
    lateinit var propertyDetailsModel: PropertyDetailsModel
    private var propertyDetailsPresenter: PropertyDetailsPresenter? = null

    private var _binding: FragmentPropertyDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPropertyDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        propertyDetailsPresenter = PropertyDetailsPresenter(
            propertyDetailsView = this,
            propertyDetailsModel = propertyDetailsModel
        )
        propertyDetailsPresenter?.screenStarted(propertyId = arguments?.getSerializable("propertyId") as String)
        binding.backButton.setOnClickListener {
            propertyDetailsPresenter?.backClicked(
                findNavController()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showProgress() {
        binding.loadingIndicator.isVisible = true
    }

    override fun hideProgress() {
        binding.loadingIndicator.isVisible = false
    }

    override fun displayPropertyDetails(propertyDetails: PropertyDetails) {
        binding.apply {
            headerName.text = propertyDetails.propertyName
            propertyName.text = propertyDetails.propertyName
            propertyType.text = propertyDetails.propertyType
            cityName.text = propertyDetails.city

            Glide.with(binding.root.context)
                .load(propertyDetails.previewImage)
                .into(previewImage)

            ratingNumber.text = propertyDetails.ratingNumber.toString()
            numberOfRatingsTitle.text =
                getString(R.string.number_of_reviews, propertyDetails.numberOfRatings)
            aboutMessage.text = propertyDetails.overview
            starRatingNumber.text = propertyDetails.ratingNumber.toString()
            starRatingDescription.text = getString(
                R.string.rating_text,
                propertyDetails.getRatingName(),
                propertyDetails.numberOfRatings
            )
            valueForMoneyBar.progress = propertyDetails.ratingBreakdown.value
            valueForMoneyRating.text =
                propertyDetails.ratingBreakdown.value.toDouble().div(10).toString()

            securityBar.progress = propertyDetails.ratingBreakdown.security
            securityRating.text =
                propertyDetails.ratingBreakdown.security.toDouble().div(10).toString()

            cleanlinessBar.progress = propertyDetails.ratingBreakdown.clean
            cleanlinessRating.text =
                propertyDetails.ratingBreakdown.clean.toDouble().div(10).toString()

            staffBar.progress = propertyDetails.ratingBreakdown.staff
            staffRating.text = propertyDetails.ratingBreakdown.staff.toDouble().div(10).toString()

            locationBar.progress = propertyDetails.ratingBreakdown.location
            locationRating.text =
                propertyDetails.ratingBreakdown.location.toDouble().div(10).toString()

            facilitiesBar.progress = propertyDetails.ratingBreakdown.facilities
            facilitiesRating.text =
                propertyDetails.ratingBreakdown.facilities.toDouble().div(10).toString()

            fullLocation.text = propertyDetails.address

            lowestPrice.text = propertyDetails.currency.symbol + propertyDetails.lowestPricePerNight
        }
    }

    override fun displayError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}