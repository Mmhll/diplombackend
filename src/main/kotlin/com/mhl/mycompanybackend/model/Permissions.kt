package com.mhl.mycompanybackend.model

import javax.persistence.*

@Entity
@Table(name = "permissions")
open class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JvmField
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var name: PermissionName? = null

    @OneToMany(mappedBy = "permissions")
    private val roles: List<Roles>? = null

    constructor()
    constructor(name: PermissionName?) {
        this.name = name
    }
}