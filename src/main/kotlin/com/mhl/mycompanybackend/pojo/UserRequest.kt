package com.mhl.mycompanybackend.pojo

data class UserRequest(
    var id: Long,
    @JvmField
    var email: String,
    @JvmField
    var firstname: String,
    @JvmField
    var lastname: String,
    @JvmField
    var middlename: String,
    @JvmField
    var phone_number: String,
)
