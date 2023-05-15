package com.diyantech.chattyapp.ModelClass.countrymodel

class MessageFormate {

    private var Username: String? = null
    private var Message: String? = null
    private var UniqueId: String? = null

    fun MessageFormat(uniqueId: String?, username: String?, message: String?) {
        Username = username
        Message = message
        UniqueId = uniqueId
    }

    fun getUsername(): String? {
        return Username
    }

    fun setUsername(username: String?) {
        Username = username
    }

    fun getMessage(): String? {
        return Message
    }

    fun setMessage(message: String?) {
        Message = message
    }

    fun getUniqueId(): String? {
        return UniqueId
    }

    fun setUniqueId(uniqueId: String?) {
        UniqueId = uniqueId
    }
}