package com.example.hostelworldchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hostelworldchallenge.HostelWorldChallengeApplication
import com.example.hostelworldchallenge.R
import com.example.hostelworldchallenge.databinding.ItemPropertyBinding
import com.example.hostelworldchallenge.models.PropertyPreview

class PropertiesAdapter(private val propertyClicked: (String) -> Unit) :
    RecyclerView.Adapter<PropertiesAdapter.PropertyViewHolder>() {

    private val properties: MutableList<PropertyPreview> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding =
            ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding)
    }

    override fun getItemCount() = properties.size

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(position)
    }

    fun loadData(newProperties: MutableList<PropertyPreview>) {
        if (newProperties != properties) {
            properties.clear()
            properties.addAll(newProperties)
            notifyDataSetChanged()
        }
    }

    inner class PropertyViewHolder internal constructor(private val binding: ItemPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val property = properties[position]
            binding.root.setOnClickListener { propertyClicked(property.id) }
            binding.apply {
                Glide.with(binding.root.context)
                    .load(property.previewImage)
                    .into(previewImage)
                featuredPropertyBanner.isVisible = property.featuredProperty
                freeCancellationBanner.isVisible = property.freeCancellation
                propertyName.text = property.propertyName
                starRatingNumber.text = property.ratingNumber.toString()
                starRatingDescription.text =
                    HostelWorldChallengeApplication.getContext()?.getString(
                        R.string.rating_text,
                        property.getRatingName(),
                        property.numberOfRatings
                    )
                shortDescriptionText.text = HostelWorldChallengeApplication.getContext()?.getString(
                    R.string.km_from_city_centre,
                    property.propertyType,
                    property.propertyDistance.toString()
                )
                freeBreakfastIcon.isVisible = property.freeBreakfast
                freeWifiIcon.isVisible = property.freeWifi

                if (property.lowestDormPricePerNight.isNotBlank() && property.lowestDormPricePerNight != "null") {
                    dormPrice.text = HostelWorldChallengeApplication.getContext()?.getString(
                        R.string.lowest_price,
                        property.currency.symbol,
                        property.lowestDormPricePerNight
                    )
                } else {
                    dormPrice.isVisible = false
                    dormsFromText.text =
                        HostelWorldChallengeApplication.getContext()
                            ?.getString(R.string.no_dorm_availability)
                }
                if (property.lowestPrivatePricePerNight.isNotBlank() && property.lowestPrivatePricePerNight != "null") {
                    privatePrice.text =
                        HostelWorldChallengeApplication.getContext()?.getString(
                            R.string.lowest_price,
                            property.currency.symbol,
                            property.lowestPrivatePricePerNight
                        )
                } else {
                    privatePrice.isVisible = false
                    privatesFromText.text =
                        HostelWorldChallengeApplication.getContext()
                            ?.getString(R.string.no_private_availability)
                }
            }
        }
    }
}