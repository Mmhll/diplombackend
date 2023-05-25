package com.mhl.mycompanybackend.model

import javax.persistence.*

@Entity
@Table(name = "roles", uniqueConstraints = [UniqueConstraint(columnNames = ["role_name"])])
class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "role_name")
    var role_name: String? = null

    @JvmField
    @ManyToOne
    @JoinColumn(name = "permission_id")
    var permissions: Permissions? = null

    constructor()
    constructor(roleName: String?, permissions: Permissions?) {
        role_name = roleName
        this.permissions = permissions
    }
}
