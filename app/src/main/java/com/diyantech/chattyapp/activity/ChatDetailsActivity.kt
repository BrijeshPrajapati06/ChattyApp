package com.diyantech.chattyapp.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.diyantech.chattyapp.R
import de.hdodenhof.circleimageview.CircleImageView
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class ChatDetailsActivity : AppCompatActivity() {

    var img_back: ImageView? = null
    var img_online_offline: ImageView? = null
    var img_voice_call: ImageView? = null
    var img_more: ImageView? = null
    var img_plus: ImageView? = null
    var img_record: ImageView? = null
    var send_msg: ImageView? = null
    var civ_user_profile: CircleImageView? = null
    var txt_user_name: TextView? = null
    var rcv_message: RecyclerView? = null
    var edt_message: EditText? = null

    //    var mSocket: Socket? = null
    lateinit var mSocket: Socket


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_details)
        mSocket = IO.socket("http://122.170.0.3:3017/")
        mSocket.on("new message", onNewMessage)
        mSocket.connect()
        Log.d("mTAG", "OnConnect-->" + mSocket.connect())
        print(mSocket.connect())


        img_back = findViewById(R.id.img_back)
        img_online_offline = findViewById(R.id.img_online_offline)
        img_voice_call = findViewById(R.id.img_voice_call)
        img_more = findViewById(R.id.img_more)
        img_plus = findViewById(R.id.img_plus)
        img_record = findViewById(R.id.img_record)
        send_msg = findViewById(R.id.send_msg)
        civ_user_profile = findViewById(R.id.civ_user_profile)
        txt_user_name = findViewById(R.id.txt_user_name)
        rcv_message = findViewById(R.id.rcv_message)
        edt_message = findViewById(R.id.edt_message)


        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)


        img_back?.setOnClickListener {
            onBackPressed()
        }

        send_msg?.setOnClickListener {
            attemptSend()
        }

    }

    private fun attemptSend() {
        val message = edt_message?.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(message)) {
            return
        }

        edt_message!!.setText("")
        edt_message?.setTextColor(getColor(R.color.black))

        mSocket.emit("new message", message)
        Log.d("mTAG", "attemptSend:--->" + message)
    }

    private val onNewMessage =
        Emitter.Listener { args ->
            runOnUiThread(Runnable {
                val data = args[0] as JSONObject
                val username: String
                val message: String
                try {
                    username = data.getString("username")
                    message = data.getString("message")
                } catch (e: JSONException) {
                    return@Runnable
                }

                // add the message to view
                addMessage(username, message)
            })
        }

    private fun addMessage(username: String, message: String) {

    }

}

