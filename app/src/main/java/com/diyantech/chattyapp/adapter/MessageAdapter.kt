package com.diyantech.chattyapp.adapter

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.activity.MainActivity
import java.text.MessageFormat

class MessageAdapter  : RecyclerView.Adapter<MessageAdapter.Holder>() {
    class Holder {

    }

    fun MessageAdapter(context: Context?, resource: Int, objects: List<MessageFormat?>?) {
        super(context, resource, objects)
    }

    fun getView(position: Int, convertView: View, parent: ViewGroup?): View? {
        var convertView = convertView
        Log.i(TAG, "getView:")
        val message: MessageFormat = getItem(position)
        if (TextUtils.isEmpty(MessageFormat.Message)) {
            convertView = (this as Activity).layoutInflater.inflate(
                R.layout.user_connected,
                parent,
                false
            )
            val messageText = convertView.findViewById<TextView>(R.id.message_body)
            Log.i(TAG, "getView: is empty ")
            val userConnected: String = message.getUsername()
            messageText.text = userConnected
        } else if (message.getUniqueId().equals(MainActivity.uniqueId)) {
            Log.i(
               TAG,
                "getView: " + message.getUniqueId().toString() + " " + uniqueId
            )
            convertView = (getContext() as Activity).layoutInflater.inflate(
                R.layout.my_message,
                parent,
                false
            )
            val messageText = convertView.findViewById<TextView>(R.id.message_body)
            messageText.setText(message.getMessage())
        } else {
            Log.i(TAG, "getView: is not empty")
            convertView = (getContext() as Activity).layoutInflater.inflate(
                R.layout.their_message,
                parent,
                false
            )
            val messageText = convertView.findViewById<TextView>(R.id.message_body)
            val usernameText = convertView.findViewById<View>(R.id.name) as TextView
            messageText.visibility = View.VISIBLE
            usernameText.visibility = View.VISIBLE
            messageText.setText(message.me)
            usernameText.setText(message.getUsername())
        }
        return convertView
    }
}