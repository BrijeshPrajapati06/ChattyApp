package com.diyantech.chattyapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.diyantech.chattyapp.R

class Welcome_Chatty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_chatty)


        Handler().postDelayed({
            val intent = Intent(this@Welcome_Chatty, MainActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}