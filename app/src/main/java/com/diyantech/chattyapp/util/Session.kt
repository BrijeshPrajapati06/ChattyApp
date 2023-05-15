package com.diyantech.chattyapp.util

import android.content.SharedPreferences
import android.util.Log
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.Data
import com.google.gson.Gson
import com.google.gson.JsonParser

class Session {

    companion object{

        private val ourInstance: Session = Session()

        private val mPrefs: SharedPreferences? = null
        private val PREF_CHECK_VRESION = "pref_check_version"

        private val gson = Gson()

        fun getInstance(): Session? {
            return ourInstance
        }

        /**
         * Sets current user.
         *
         * @param Data the LoginResponse object
         */
        fun setCurrentUser(data: Data?) {
            PrefHelper.instance?.setString("user", Gson().toJson(data))
        }


        fun getCurrentUser(): Data? {
            val strUser: String = PrefHelper.instance?.getString("user", "")!!
            Log.d("TAG", "getCurrentUser: ---->$strUser")
            return if (strUser.isEmpty()) {
                null
            } else {
                val parser = JsonParser()
                val mJson = parser.parse(strUser)
                val gson = Gson()
                gson.fromJson(
                    mJson,
                    Data::class.java
                )
            }
        }

    }


}