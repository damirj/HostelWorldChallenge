package com.example.hostelworldchallenge.network

import com.example.hostelworldchallenge.network.models.ExchangeRate
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class ExchangeRatesRepository @Inject constructor(
    private val exchangeRatesService: ExchangeRatesService
) {

    fun getExchangeRates(): Flowable<ExchangeRate> {
        return exchangeRatesService.getExchangeRates()
    }
}