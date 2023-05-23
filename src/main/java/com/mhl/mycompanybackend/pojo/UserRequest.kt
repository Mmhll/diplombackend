package com.mhl.mycompanybackend.pojo

class UserRequest {
    var id: Long? = null
        private set
    @JvmField
    var email: String? = null
    @JvmField
    var firstname: String? = null
    @JvmField
    var lastname: String? = null
    @JvmField
    var middlename: String? = null
    @JvmField
    var phone_number: String? = null

    constructor()
    constructor(id: Long?, email: String?, firstname: String?, lastname: String?, middlename: String?, phoneNumber: String?) {
        this.id = id
        this.email = email
        this.firstname = firstname
        this.lastname = lastname
        this.middlename = middlename
        phone_number = phoneNumber
    }
}
