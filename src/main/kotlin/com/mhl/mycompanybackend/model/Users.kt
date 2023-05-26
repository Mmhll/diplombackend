package com.mhl.mycompanybackend.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username"]), UniqueConstraint(columnNames = ["email"])])
open class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var username: String? = null
    var email: String? = null

    @JsonIgnore
    var password: String? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: MutableList<Roles> = mutableListOf()

    @JvmField
    @OneToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var userData: UserData? = null

    @JsonBackReference(value = "message-user")
    @OneToMany(mappedBy = "user")
    var messages: List<Message> = ArrayList()

    @JsonBackReference(value = "task-creator")
    @OneToMany(mappedBy = "creator")
    var task_creator: List<Tasks>? = null

    /*@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})*/
    @JsonBackReference(value = "task-executor")
    @OneToMany(mappedBy = "executor")
    var task_executor: List<Tasks>? = null

    @JsonBackReference(value = "task-members")
    @ManyToMany(mappedBy = "members", cascade = [CascadeType.PERSIST], fetch = FetchType.EAGER)
    private val tasks: List<Tasks> = ArrayList()

    constructor()
    constructor(username: String?, email: String?, password: String?, userData: UserData?) {
        this.username = username
        this.email = email
        this.password = password
        this.userData = userData
    }

    constructor(username: String?, email: String?, userData: UserData?) {
        this.username = username
        this.email = email
        this.userData = userData
    }
}
