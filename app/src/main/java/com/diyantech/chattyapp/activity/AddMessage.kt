package com.diyantech.chattyapp.activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.diyantech.chattyapp.R

class AddMessage : AppCompatActivity() {

    private var setNickName: Button? = null
    private var userNickName: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_message)

        userNickName = findViewById(R.id.userNickName)
        setNickName = findViewById(R.id.setNickName)

        userNickName?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().trim { it <= ' ' }.length > 0) {
                    setNickName?.setEnabled(true)
                    Log.i(ContentValues.TAG, "onTextChanged: ABLED")
                } else {
                    Log.i(ContentValues.TAG, "onTextChanged: DISABLED")
                    setNickName?.setEnabled(false)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        setNickName?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@AddMessage, MainActivity::class.java)
            intent.putExtra("username", userNickName?.getText().toString())
            startActivity(intent)
        })
    }
}