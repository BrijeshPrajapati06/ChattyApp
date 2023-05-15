package com.diyantech.chattyapp.activity

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.diyantech.chattyapp.BuildConfig
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.Data
import com.diyantech.chattyapp.ModelClass.countrymodel.userupdate.UserUpdateResponse
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.api.ApiInterface
import com.diyantech.chattyapp.api.ApiUtility
import com.diyantech.chattyapp.util.Session
import com.diyantech.chattyapp.util.Util
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

open class Profile_Activity : AppCompatActivity() {

    lateinit var img_civ_profile: CircleImageView
    var random = Random()
    var last_count = 0
    var mBundle: Bundle? = null
    var Str_id: String? = null
    var Str_image: String? = null
    var Str_name: String? = null
    var Str_token: String? = null
    var Str_mobile: String? = null
    var btn_next_profile: Button? = null
    var APP_FILE: File? = null
    private lateinit var PERMISSIONS: Array<String>
    var edt_name: EditText? = null
    var mPart: MultipartBody.Part? = null
    var Userdata : Data ?= null
    var txt_name_count : TextView?=null



    var userUpdateResponse: UserUpdateResponse? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val btn_next = findViewById<Button>(R.id.btn_next_profile)
        edt_name = findViewById(R.id.edt_name)
        val img_edit = findViewById<ImageView>(R.id.img_edit)
        img_civ_profile = findViewById(R.id.img_civ_profile)
        txt_name_count = findViewById(R.id.txt_name_count)
        btn_next_profile = findViewById(R.id.btn_next_profile)


        PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Session.getCurrentUser() != null){
            Userdata = Session.getCurrentUser()

            Str_id = Userdata?._id
            Str_image = Userdata?.profile_image
            Str_name = Userdata?.userName
        }
       /* mBundle = getIntent().getExtras()
        if (mBundle != null) {
            Str_id = mBundle!!.getString("User_Id")
            Str_token = mBundle!!.getString("token")
            Log.e(TAG, "userId " + Str_id)
            Str_image = mBundle!!.getString("profile_image", "")
            Str_name = mBundle!!.getString("user_name_key", "")
            Str_mobile = mBundle!!.getString("mobile_no_key", "")
        }*/
        edt_name?.setText(Str_name)
//        val count = 25 - Str_name!!.length
//        txt_name_count?.text = "" + count
        val image: String = "http://122.170.0.3:3017/uploads/photos/" + Str_image
        Log.d(TAG, "onCreate: -->$image")

        Glide.with(this)
            .load(image)
            .error(R.drawable.profile)
            .into(img_civ_profile)

        btn_next_profile?.setOnClickListener {
            if (edt_name?.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
            } else {
                userUpdateApi()
            }

        }
        edt_name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("TAG", "count--->$count")
                Log.d("TAG", "s--->" + s.length)
                if (count > 25) {
                    txt_name_count?.setText("" + (1 + s.length))
                    last_count = s.length
                } else if (count < 25) {
                    txt_name_count?.setText("" + (25 - s.length))
                    last_count = s.length
                }
                Log.d("TAG", "plus--->$last_count")

                /* if (count<25){
                    txt_name_count.setText("" + (Integer.parseInt(txt_name_count.getText().toString())-1));

                }else{
                    //txt_name_count.setText("" + (Integer.parseInt(txt_name_count.getText().toString())+1));
                }*/
            }

            override fun afterTextChanged(s: Editable) {}
        })
        img_edit.setOnClickListener {
            if (!HasPermission(this@Profile_Activity, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this@Profile_Activity, PERMISSIONS, 1)
            } else {
                selectImage()
            }
        }


    }

    private fun HasPermission(
        profile_activity: Profile_Activity?,
        permissions: Array<String>?
    ): Boolean {
        if (profile_activity != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        profile_activity,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            } else {
                // Toast.makeText(this,"calling permission is denied",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Allow to access your camera and storage?")
                    .setPositiveButton(
                        android.R.string.yes
                    ) { dialog, which ->
                        val i = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                        )
                        startActivity(i)
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
    }

    private fun userUpdateApi() {

        val requestData = HashMap<String, RequestBody>()

        if (APP_FILE != null) {
            mPart = prepareFilePart("profile_image", APP_FILE!!)
            requestData.put("userName", createPartFromString(edt_name!!.text.toString()))
            requestData.put("_id", createPartFromString(Str_id!!))
            updateUserWithProfile(mPart!!, requestData);
        } else {
            requestData.put("userName", createPartFromString(edt_name!!.text.toString()))
            requestData.put("_id", createPartFromString(Str_id!!))

            mPart = null;
            updateUserWithProfile(mPart, requestData);
        }
    }

    private fun updateUserWithProfile(
        mPart: MultipartBody.Part?,
        requestData: HashMap<String, RequestBody>
    ) {

        if (Util.checkNetwork(applicationContext)) {

            val apiInterface: ApiInterface = ApiUtility.getUser().create(ApiInterface::class.java)
            var call: Call<UserUpdateResponse>? = null

            if (mPart != null) {
                call = apiInterface.updateUserP(mPart, requestData)
            } else {
                call = apiInterface.updateUser(requestData)
            }

            call.enqueue(object : Callback<UserUpdateResponse> {
                override fun onResponse(
                    call: Call<UserUpdateResponse>,
                    response: Response<UserUpdateResponse>
                ) {
                    Toast.makeText(this@Profile_Activity, "Api call  ", Toast.LENGTH_SHORT).show()

                    if (response.isSuccessful) {

                        userUpdateResponse = response.body()

                        if (userUpdateResponse?.meta?.code == 200) {
                            val data = Data(userName = userUpdateResponse?.data?.userName ?: "",
                                            profile_image = userUpdateResponse?.data?.profile_image ?: "",
                                            countryCode = Userdata?.countryCode ?: "+91",
                                            countryName = Userdata?.countryName ?: "",
                                            createdAt = Userdata?.createdAt ?: "",
                                            _id = Userdata?._id ?: "",
                                            isverified = Userdata?.isverified ?: false,
                                            mobileNo = Userdata?.mobileNo ?: "",
                                            mytoken = Userdata?.mytoken ?: "",
                                            status = Userdata?.status ?: 0,
                                            updatedAt = userUpdateResponse?.data?.updatedAt ?: "")

                            Session.setCurrentUser(data)
                            val intent = Intent(this@Profile_Activity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Profile_Activity, "" + response.body()?.meta?.message, Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@Profile_Activity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<UserUpdateResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            Toast.makeText(this, "please check your internet connection ", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun createPartFromString(toString: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, "" + toString)
    }

    private fun prepareFilePart(s: String, appFile: File): MultipartBody.Part {
        val MEDIA_TYPE_PNG: MediaType = "image/*".toMediaTypeOrNull()!!
        val requestFile: RequestBody = RequestBody.create(MEDIA_TYPE_PNG, appFile)
        return createFormData(s, appFile.getName(), requestFile)
    }

    private fun selectImage() {
        val dialog = Dialog(this@Profile_Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.image_upload_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btn_camera = dialog.findViewById<Button>(R.id.btn_camera)
        val btn_gallery = dialog.findViewById<Button>(R.id.btn_gallery)
        val txt_cancel = dialog.findViewById<TextView>(R.id.txt_cancel)
        val img_camera = dialog.findViewById<ImageView>(R.id.img_camera)
        val img_gallery = dialog.findViewById<ImageView>(R.id.img_gallery)
        val lin_camera = dialog.findViewById<LinearLayout>(R.id.lin_camera)
        val lin_gallery = dialog.findViewById<LinearLayout>(R.id.lin_gallery)


        lin_camera.setBackgroundResource(R.drawable.rectangle_shape_line)
        lin_camera.setOnClickListener {

            lin_camera.setBackgroundResource(R.drawable.rectangle_shape_line)
            lin_gallery.background = null

        }

        lin_gallery.setOnClickListener {
            lin_gallery.setBackgroundResource(R.drawable.rectangle_shape_line)
            lin_camera.background = null
        }

        lin_camera.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)
            dialog.dismiss()
        }

        lin_gallery.setOnClickListener {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
            dialog.dismiss()
        }
        txt_cancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                val extras = data?.extras
                val imageBitmap = extras!!["data"] as Bitmap?
                val tempUri: Uri = getImageUri(applicationContext, imageBitmap)
                val finalFile: File = File(getRealPathFromURI(tempUri))
                Log.e("TAG", "camera----->${finalFile.path}")
                APP_FILE = finalFile

                Log.e("TAG", "app_file----->" + APP_FILE!!.path)
                Glide.with(this)
                    .load(finalFile.path)
                    .error(R.drawable.profile)
                    .into(img_civ_profile!!)
            }
            1 -> if (resultCode == RESULT_OK) {
                val selectedImage = data?.data
                val finalFile: File = File(selectedImage?.let { getRealPathFromURI(it) })
                Log.e("TAG", "gallery----->$finalFile")
                APP_FILE = finalFile
                Glide.with(this)
                    .load(finalFile.path)
                    .error(R.drawable.profile)
                    .into(img_civ_profile!!)
            }
        }
    }

    private fun getImageUri(applicationContext: Context?, imageBitmap: Bitmap?): Uri {
        val id = String.format("%04d", random.nextInt(10000))
        val bytes = ByteArrayOutputStream()
        imageBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            applicationContext!!.contentResolver,
            imageBitmap,
            id,
            null
        )
        return Uri.parse(path)
    }


    fun getRealPathFromURI(tempUri: Uri): String? {
        var path: String = ""
        if (contentResolver != null) {
            val cursor = contentResolver.query(tempUri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }
}