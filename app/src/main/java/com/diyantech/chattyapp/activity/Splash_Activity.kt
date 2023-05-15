package com.diyantech.chattyapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.diyantech.chattyapp.R

class Splash_Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val getStart = findViewById<TextView>(R.id.getStart)

        getStart.setOnClickListener {
                val intent = Intent(this, SplashScreen::class.java)
                startActivity(intent)
                finish()

        }



    }
}