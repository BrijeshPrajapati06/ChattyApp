package com.diyantech.chattyapp.ModelClass.countrymodel.createusermodel


data class UserRequest(
    val countryCode: String,
    val countryName: String,
    val mobileNo: String
)