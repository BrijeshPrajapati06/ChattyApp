package com.diyantech.chattyapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLUE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.CalendarContract.Colors
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.diyantech.chattyapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val txt_privacy_condition = findViewById<TextView>(R.id.txt_privacy_condition)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        val SpanString = SpannableString(
            "Read our Privacy Policy. Tap Continue Accept the Terms of Services."
        )

        val teremsAndCondition: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

                Toast.makeText(this@SplashScreen, "Tearms & Condition", Toast.LENGTH_SHORT).show()
            }

        }
        val privacy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(this@SplashScreen, "privacy", Toast.LENGTH_SHORT).show()

            }
        }

        SpanString.setSpan(teremsAndCondition, 9, 24, 0)
        SpanString.setSpan(privacy, 48, 67, 0)
        SpanString.setSpan(ForegroundColorSpan(Color.BLUE), 9, 24, 0)
        SpanString.setSpan(ForegroundColorSpan(Color.BLUE), 48, 67, 0)

        txt_privacy_condition.setMovementMethod(LinkMovementMethod.getInstance())
        txt_privacy_condition.setText(SpanString, TextView.BufferType.SPANNABLE)
        txt_privacy_condition.setSelected(true)


        btnContinue.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)

        }
    }
}