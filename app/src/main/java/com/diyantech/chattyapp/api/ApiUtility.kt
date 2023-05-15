package com.diyantech.chattyapp.api

import android.content.SharedPreferences
import android.util.Log
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.Data
import com.diyantech.chattyapp.util.Session
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtility {
    var sharedPreferences: SharedPreferences ? = null


    var token = ""
    var Userdata : Data ?= null


    fun getUser(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

       // sharedPreferences = MyApp.instance.getSharedPreferences("myPref", MODE_PRIVATE)
       // token = sharedPreferences?.getString("token","").toString()

        if (Session.getCurrentUser() != null){
            Userdata = Session.getCurrentUser()
        }
        Log.d("TAG", "token: --->"+ Userdata?.mytoken)

        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer "+Userdata?.mytoken
                )
                .build()
            return@Interceptor chain.proceed(newRequest)
        }).addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("http://122.170.0.3:3017/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    fun apiRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Content-Type",
                    "application/json"
                )
                .build()
            return@Interceptor chain.proceed(newRequest)
        }).addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("http://122.170.0.3:3017/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


}

