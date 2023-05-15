package com.diyantech.chattyapp.ModelClass.countrymodel


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Data(
    @Expose
    @SerializedName("countryCode")
    val countryCode: String,
    @Expose
    @SerializedName("isoCode")
    val isoCode: String,
    @Expose
    @SerializedName("name")
    val name: String

)