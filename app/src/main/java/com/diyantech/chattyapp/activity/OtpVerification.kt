package com.diyantech.chattyapp.activity



import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.api.ApiInterface
import com.diyantech.chattyapp.api.ApiUtility
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.OtpRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.OtpResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.resendotp.ResendOtpRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.resendotp.ResendOtpResponse
import com.diyantech.chattyapp.util.Session
import com.diyantech.chattyapp.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class OtpVerification : AppCompatActivity() {
    var txt_time: TextView? = null
    private var btn_verify: Button? = null
    var txt_otp: TextView? = null
    private var linear_Otp: LinearLayout? = null
    var otpResponse: OtpResponse? = null
    private var txt_edit_number: TextView? = null
    var et1: EditText? = null
    var et2: EditText? = null
    var et3: EditText? = null
    var et4: EditText? = null
    private val startTime = (15 * 1000).toLong()
    private val interval = (1 * 1000).toLong()
    private var sharedPreferences: SharedPreferences? = null
    private var editor: Editor? = null
    private var mobileNumber: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.apply()

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        et4 = findViewById(R.id.et4)
        btn_verify = findViewById(R.id.btn_verify)
        txt_otp = findViewById(R.id.txt_otp)
        txt_time = findViewById(R.id.txt_time)
        linear_Otp = findViewById(R.id.linear_Otp)
        txt_edit_number = findViewById(R.id.txt_edit_number)
        mobileNumber = intent.getStringExtra("mobile_no_key")

        startTimer()

        et1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et1?.text.toString().length == 1) {
                    et1?.clearFocus()
                    et2?.requestFocus()
                    et2?.setCursorVisible(true)}
            }
            override fun afterTextChanged(p0: Editable?) {
                if (et1?.text.toString().length == 0) {
                    et1?.requestFocus()
                }
            }

        })
        et2?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et2?.text.toString().length == 1) {
                    et2?.clearFocus()
                    et3?.requestFocus()
                    et3?.setCursorVisible(true)
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if (et2?.text.toString().length == 0) {
                    et2?.requestFocus()
                }
            }
        })

        et1?.setOnKeyListener(GenericKeyEvent(et1!!, null))
        et2?.setOnKeyListener(GenericKeyEvent(et2!!, et1))
        et3?.setOnKeyListener(GenericKeyEvent(et3!!, et2))
        et4?.setOnKeyListener(GenericKeyEvent(et4!!, et3))

        et3?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et3?.text.toString().length == 1) {
                    et3?.clearFocus()
                    et4?.requestFocus()
                    et4?.setCursorVisible(true)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (et3?.text.toString().length == 0) {
                    et3?.requestFocus()
                }
            }

        })

        et4?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (et4?.text.toString().length == 1) {
                    et4?.clearFocus()
                    et4?.requestFocus()
                    et4?.setCursorVisible(true)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        txt_edit_number?.setOnClickListener {
            onBackPressed()
        }

        txt_otp?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                txt_time?.visibility = View.VISIBLE

            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        })


        txt_otp?.setOnClickListener {
            if (txt_time?.getVisibility() == View.GONE) {
                val hashMap = HashMap<String, Any>()
                hashMap.put("mobileNo", mobileNumber!!)
                otpResendApi()
            } else {
                txt_otp?.isClickable = true
            }
        }

//        txt_otp?.setOnClickListener {
//            otpResendApi()
//        }

        btn_verify?.setOnClickListener {
            otpVerificationApi()
        }

    }

    private fun otpResendApi() {

        if (Util.checkNetwork(applicationContext)) {

            val resendOtpRequest = ResendOtpRequest(
                mobileNo = mobileNumber!!
            )
            val countryInterface: ApiInterface =
                ApiUtility.getUser().create(ApiInterface::class.java)

            val call: Call<ResendOtpResponse> = countryInterface.sendResendOtp(resendOtpRequest)

            call.enqueue(object : Callback<ResendOtpResponse> {
                override fun onResponse(
                    call: Call<ResendOtpResponse>,
                    response: Response<ResendOtpResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.meta.code == 200) {
                                Toast.makeText(
                                    this@OtpVerification,
                                    "OTP Send Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startTimer()
                            } else {
                                if (response.body()!!.meta.message != null) { Toast.makeText(this@OtpVerification, response.body()!!.meta.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@OtpVerification,
                            "" + response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResendOtpResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })


        }
    }

    private fun otpVerificationApi() {
        if (valid()) {

            val otp =
                et1?.text.toString() + et2?.text.toString() + et3?.text.toString() + et4?.text.toString()
            val otpRequest = OtpRequest(
                mobileNo = mobileNumber!!,
                OTP = otp
            )

            if (Util.checkNetwork(applicationContext)) {
                val countryInterface: ApiInterface =
                    ApiUtility.apiRetrofit().create(ApiInterface::class.java)

                val call: Call<OtpResponse> = countryInterface.otpVerification(otpRequest)

                call.enqueue(object : Callback<OtpResponse> {
                    override fun onResponse(
                        call: Call<OtpResponse>,
                        response: Response<OtpResponse>
                    ) {
                        if (response.isSuccessful) {

                            otpResponse = response.body()
                            if (otpResponse?.meta?.code == 200) {

                                Session.setCurrentUser(otpResponse?.data)
                                /*if (otpResponse?.data != null){

                                }*/

                                val intent = Intent(this@OtpVerification, Profile_Activity::class.java)
                                startActivity(intent)

                            } else {
                                Toast.makeText(
                                    this@OtpVerification,
                                    "" + response.body()?.meta?.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        } else {
                            Toast.makeText(
                                this@OtpVerification,
                                "Response not success",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                    override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                        Toast.makeText(
                            this@OtpVerification,
                            "error: " + t.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            } else {
                Toast.makeText(
                    this@OtpVerification,
                    "please check your internet connection ",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun valid(): Boolean {
        if (et1!!.text.toString().trim().isEmpty() && et2!!.text.toString().trim().isEmpty()
            && et3!!.text.toString().trim().isEmpty() && et4!!.text.toString().trim().isEmpty()
        ) {
            Toast.makeText(this, "Please Enter Otp", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun startTimer() {
        txt_time?.setVisibility(View.VISIBLE)
        object : CountDownTimer(startTime, interval) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val currentTime = millisUntilFinished / 1000
                txt_time?.setText("" + "0" + currentTime / 60 + " : " + if (currentTime % 60 >= 10) currentTime % 60 else "0" + currentTime % 60)
                //txt_time.setText("seconds remaining: " +String.valueOf(millisUntilFinished/1000));

//                txt_otp.setVisibility(View.GONE);
            }

            override fun onFinish() {
                txt_time?.setVisibility(View.GONE)
                txt_otp?.setVisibility(View.VISIBLE)
            }

        }.start()
    }
}

class GenericKeyEvent(private val currentView: EditText, private val previousView: EditText?) :
    View.OnKeyListener {
    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if (p2!!.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_DEL && currentView.id != R.id.et1 && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView!!.text = null
            previousView.requestFocus()
            return true
        }
        return false
    }
}
