package com.diyantech.chattyapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL = "http://122.170.0.3:3017/v1/"

object CountryUtility {
    fun getCountryData() : Retrofit{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization",
                    "Bearer " + "f1bffd67d95052b8b29935c77f00f469846cb6bac9aa44d5b19674e0cc829e21be56b65f9791578b")
                .build()
            return@Interceptor  chain.proceed(newRequest)
        }).addInterceptor(interceptor).build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

    }
}