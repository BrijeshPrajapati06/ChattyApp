package com.diyantech.chattyapp.ModelClass.countrymodel.modelotp


data class Data(
    var userName: String,
    var profile_image: String,
    val countryCode: String,
    val countryName: String,
    val createdAt: String,
    val _id: String,
    val isverified: Boolean,
    val mobileNo: String,
    val mytoken: String,
    val status: Int,
    val updatedAt: String
)