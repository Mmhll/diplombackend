package com.mhl.mycompanybackend.pojo

class MessageRequest {
    @JvmField
    var user_id: Long? = null
    @JvmField
    var message: String? = null
    @JvmField
    var chat_id: Long? = null
}
