package com.diyantech.chattyapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.diyantech.chattyapp.R

class SettingActivity : AppCompatActivity() {

    var img_back : ImageView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        img_back=findViewById(R.id.img_back)

        img_back?.setOnClickListener {
            val intent = Intent(this@SettingActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}