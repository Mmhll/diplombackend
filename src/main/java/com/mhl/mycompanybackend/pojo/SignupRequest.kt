package com.mhl.mycompanybackend.pojo

class SignupRequest {
    var email: String? = null
        private set
    var firstname: String? = null
        private set
    var lastname: String? = null
        private set
    var middlename: String? = null
        private set
    @JvmField
    var password: String? = null
    @JvmField
    var phone_number: String? = null

    constructor(email: String?, firstname: String?, lastname: String?, middlename: String?, password: String?, phoneNumber: String?) {
        this.email = email
        this.firstname = firstname
        this.lastname = lastname
        this.middlename = middlename
        this.password = password
        phone_number = phoneNumber
    }

    constructor()
}
