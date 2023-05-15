package com.diyantech.chattyapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.Data
import com.diyantech.chattyapp.util.Session

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user : Data ?= null
        if (Session.getCurrentUser() != null){
            user = Session.getCurrentUser()!!
        }

        Handler().postDelayed(object : Runnable {
            override fun run() {

                var intent : Intent ?= null

                if (user == null){
                    intent = Intent(this@MainActivity,Splash_Activity::class.java)
                }else{
                    if (user?.userName == null){
                        intent = Intent(this@MainActivity,Profile_Activity::class.java)
                    }else{
                        intent = Intent(this@MainActivity,HomeActivity::class.java)

                    }
                }
                startActivity(intent)
                finish()
            }
        }, 2000)
    }
}