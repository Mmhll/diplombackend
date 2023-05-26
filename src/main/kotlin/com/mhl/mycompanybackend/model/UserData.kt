package com.mhl.mycompanybackend.model

import javax.persistence.*

@Entity
@Table(name = "user_data")
open class UserData {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @JvmField
    var firstname: String? = null
    var lastname: String? = null
    var middlename: String? = null
    var phone_number: String? = null

    @OneToOne(mappedBy = "userData")
    private val user: Users? = null

    constructor(firstname: String?, lastname: String?, middlename: String?, phoneNumber: String?) {
        this.firstname = firstname
        this.lastname = lastname
        this.middlename = middlename
        phone_number = phoneNumber
    }

    constructor()
}
