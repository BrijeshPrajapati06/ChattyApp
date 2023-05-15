package com.diyantech.chattyapp.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.api.ApiInterface
import com.diyantech.chattyapp.api.CountryUtility
import com.diyantech.chattyapp.api.ApiUtility
import com.diyantech.chattyapp.ModelClass.countrymodel.CountryResponse
import com.diyantech.chattyapp.ModelClass.countrymodel.Data
import com.diyantech.chattyapp.ModelClass.countrymodel.createusermodel.UserRequest
import com.diyantech.chattyapp.ModelClass.countrymodel.createusermodel.UserResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity() {


    var spinner: Spinner? = null
    var btn_next_reg: Button? = null
    var sharedPref: SharedPreferences? = null
    var user: UserResponse? = null
    var error_mobile: TextView?=null
    var arrayCountryList = ArrayList<String>()
    var PREFS_KEY = "prefs"
    private var countryid = ""
    var txtselectcountry: TextView? = null
    var txtcountrycode: TextView? = null
    var mDataList: List<Data>? = null
    var Country_Adapter: ArrayAdapter<String>? = null
    var et_mobile_number: EditText? = null


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btn_next_reg = findViewById(R.id.btn_next_reg)
        et_mobile_number = findViewById(R.id.et_mobile_number)
        error_mobile = findViewById(R.id.error_mobile)
        spinner = findViewById(R.id.spinner)
        txtselectcountry = findViewById(R.id.txtselectcountry)
        txtcountrycode = findViewById(R.id.txtcountrycode)
        error_mobile?.visibility = View.GONE

        sharedPref = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        et_mobile_number?.setTextColor(Color.parseColor("#FF000000"))

        callCountryApi()
        callUserApi()

        et_mobile_number?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                et_mobile_number?.setBackgroundResource(R.drawable.rectangle_shape_line)
                error_mobile?.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable) {}
        })



        spinner?.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                if (mDataList != null && mDataList!!.size > 0) {
                    txtselectcountry?.visibility = View.GONE
                    countryid = mDataList!!.get(position).countryCode
                    txtcountrycode?.text = countryid
                    Log.d("tag", "position-->$countryid")
                } else {
                    Toast.makeText(this@RegistrationActivity, "failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    @SuppressLint("SuspiciousIndentation")
    private fun callUserApi() {
        var connected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        connected = networkInfo != null && networkInfo.isConnected

//        if (sharedPref?.getBoolean("isLogged", true)!!) {
//
//            val intent = Intent(this@RegistrationActivity, Profile_Activity::class.java)
//            startActivity(intent)
//            finish()
//        } else {
            btn_next_reg?.setOnClickListener {
                if (valid()) {
                    val hashMap = HashMap<String, Any>()
                    hashMap["mobileNo"] = et_mobile_number?.getText().toString()
                    Log.d("TAG", "mobileNo-->" + et_mobile_number?.getText().toString())

                    val createUser: String = et_mobile_number?.text.toString().trim { it <= ' ' }

                    val userRequest = UserRequest(
                        countryCode = "+91",
                        countryName = "India",
                        mobileNo = createUser
                    )

                    if (connected) {

                        Log.e(ContentValues.TAG, "onCreate: " + userRequest)


                        val countryInterface: ApiInterface =
                            ApiUtility.getUser().create(ApiInterface::class.java)

                        val call: Call<UserResponse> = countryInterface.sendData(userRequest)

                        call.enqueue(object : Callback<UserResponse> {

                            override fun onResponse(
                                call: Call<UserResponse>,
                                response: Response<UserResponse>,
                            ) {

                                if (response.isSuccessful) {
                                    Log.d(
                                        "TAG",
                                        "ResposeCode " + (response.body()?.meta?.code ?: "Hello")
                                    )
                                    if (response.body()?.meta?.code == 200) {
                                        savePrefsData()
                                        saveUser()
                                        user = response.body()!!
                                        Toast.makeText(this@RegistrationActivity, "Successfully", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@RegistrationActivity,OtpVerification::class.java)
                                        intent.putExtra("mobile_no_key", et_mobile_number?.getText().toString())
                                        intent.putExtra("country_code_key", txtcountrycode?.getText().toString()
                                        )
                                        startActivity(intent)
                                    }
                                } else {
                                    Toast.makeText(
                                        this@RegistrationActivity, "Failed ", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                Toast.makeText(
                                    this@RegistrationActivity,
                                    "error: " + t.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        })

                    } else {
                        Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }
//        }
    }

    private fun callCountryApi() {
        val countryInterface: ApiInterface =
            CountryUtility.getCountryData().create(ApiInterface::class.java)

        val call: Call<CountryResponse> = countryInterface.sendCountryData()

        call.enqueue(object : Callback<CountryResponse> {
            override fun onResponse(
                call: Call<CountryResponse>,
                response: Response<CountryResponse>
            ) {
                if (response.isSuccessful && response.body()?.data != null) {
                    mDataList = response.body()!!.data
                    CreateCountryListArray(mDataList!!)
                }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Toast.makeText(getApplicationContext(), t.message, Toast.LENGTH_SHORT).show()

            }

        })
    }


    private fun CreateCountryListArray(mDataList: List<Data>) {
        Log.e(TAG, "dataList:--->" + mDataList.size)

        var pos = 0
        for (i in mDataList.indices) {
            arrayCountryList.add(mDataList.get(i).name)
            if (mDataList.get(i).name.equals("India")) {
                pos = i
            }
        }
        Country_Adapter = ArrayAdapter(
            this@RegistrationActivity,
            android.R.layout.simple_spinner_dropdown_item,
            arrayCountryList
        )
        spinner?.adapter = Country_Adapter
        spinner?.setSelection(pos)

    }



        open fun valid(): Boolean {
            var isvalid = true
            if (!mobileNo(
                    et_mobile_number?.getText().toString()
                )
            ) {
                error_mobile!!.visibility = View.VISIBLE
                et_mobile_number?.setBackgroundResource(R.drawable.error_bg)
                isvalid = false
            }
            return isvalid
        }

    private fun mobileNo(str: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher

        val PHONE_NO = "[0-9]{10}"

        pattern = Pattern.compile(PHONE_NO)
        matcher = pattern.matcher(str)

        return matcher.matches()
    }
//        if (et_mobile_number!!.text.toString().trim().isEmpty()) {
//            et_mobile_number!!.error = "Mobile Number Required"
//            et_mobile_number!!.requestFocus()
//            return false
//        } else if (et_mobile_number!!.length() < 10) {
//            et_mobile_number!!.error = "Mobile must be minimum 10 character Required"
//            et_mobile_number!!.requestFocus()
//            return false
//        }
//        return true



    private fun saveUser() {
        val editor = sharedPref?.edit()
        editor?.putBoolean("isLogged", true)
        editor?.apply()
    }

    private fun savePrefsData() {

        val prefsEditor: SharedPreferences.Editor? = sharedPref?.edit()
        val gson = Gson()
        val json = gson.toJson(user) // myObject - instance of MyObject

        prefsEditor?.putString("MyObject", json)
        prefsEditor?.commit()
    }
}

