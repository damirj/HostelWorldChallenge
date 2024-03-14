package com.example.hostelworldchallenge.network

import com.example.hostelworldchallenge.network.models.ExchangeRate
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface ExchangeRatesService {

    // In a non-test app we wouldn't store the access_key like this.
    // Also in a real app, we would make sure that the Base currency is set properly.
    @GET("latest?access_key=ffe8a856c012dd5270d4195ef12babe3&symbols=USD,GBP")
    fun getExchangeRates(): Flowable<ExchangeRate>
}