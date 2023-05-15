package com.diyantech.chattyapp.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.diyantech.chattyapp.MyApp

class Util {


    companion object {
        public fun  checkNetwork(context: Context) : Boolean {
            var connected = false
            val connectivityManager = MyApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            connected = networkInfo != null && networkInfo.isConnected
            return connected
        }
    }


}