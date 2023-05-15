package com.diyantech.chattyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diyantech.chattyapp.R
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


@Suppress("UNREACHABLE_CODE")
class ChatsFragment : Fragment() {

    var mSocket: Socket? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
        mSocket?.connect();

        {
            try {
                mSocket = IO.socket("http://chat.socket.io")
            } catch (e: URISyntaxException) {
            }
        }

    }
}