package com.diyantech.chattyapp.api

import com.diyantech.chattyapp.ModelClass.countrymodel.CountryResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.createusermodel.UserRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.createusermodel.UserResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.OtpRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.OtpResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.resendotp.ResendOtpRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.resendotp.ResendOtpResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.userupdate.UserUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface ApiInterface {

    @POST("user/countryList")
    fun sendCountryData(): Call<CountryResponse>

    @POST("user/userCreate")
    fun sendData(
        @Body userRequest: UserRequest
    ) : Call<UserResponse>

    @POST("user/checkOtpVerificationForUser")
    fun otpVerification(@Body otpRequest: OtpRequest) : Call<OtpResponse>

    @POST("user/resendOtpUser")
    fun sendResendOtp(
        @Body resendOtpRequest: ResendOtpRequest
    ) : Call<ResendOtpResponse>


    @Multipart
    @POST("user/userUpdate")
    fun updateUser(@PartMap param: HashMap<String, RequestBody>): Call<UserUpdateResponse>

    @Multipart
    @POST("user/userUpdate")
    fun updateUserP(@Part filepart: MultipartBody.Part,  @PartMap param: HashMap<String, RequestBody>): Call<UserUpdateResponse>


}