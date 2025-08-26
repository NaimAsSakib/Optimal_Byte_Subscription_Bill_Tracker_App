package com.cmedhealth.flutter.optimalbytetask.apiService

import com.cmedhealth.flutter.optimalbytetask.model.ExchangeRateResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("v6/latest/BDT")
    suspend fun getExchangeRates(): ExchangeRateResponse

    companion object {
        private const val BASE_URL = "https://open.er-api.com/"

        fun create(): CurrencyApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyApiService::class.java)
        }
    }
}