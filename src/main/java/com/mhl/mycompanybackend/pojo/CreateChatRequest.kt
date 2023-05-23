package com.mhl.mycompanybackend.pojo

class CreateChatRequest {
    @JvmField
    var name: String? = null
    @JvmField
    var members: List<Long> = ArrayList()
}
