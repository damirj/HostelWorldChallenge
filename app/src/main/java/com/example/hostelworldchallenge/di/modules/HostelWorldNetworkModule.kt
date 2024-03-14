package com.example.hostelworldchallenge.di.modules

import com.example.hostelworldchallenge.network.ExchangeRatesService
import com.example.hostelworldchallenge.network.HostelWorldService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class HostelWorldNetworkModule {

    @Provides
    @Named("hostel")
    fun providesRetrofitHostelWorld(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/PedroTrabulo-Hostelworld/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun providesHostelWorldService(@Named("hostel")retrofit: Retrofit): HostelWorldService {
        return retrofit.create(HostelWorldService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class ExchangeRatesModule {

    @Provides
    @Named("exhange")
    fun providesRetrofitExchangeRates(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl("http://api.exchangeratesapi.io/v1/")
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideExchangeRatesService(@Named("exhange")retrofit: Retrofit): ExchangeRatesService {
        return retrofit.create(ExchangeRatesService::class.java)
    }
}
