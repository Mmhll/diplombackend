package com.mhl.mycompanybackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tasks") // Таблица tasks, хранящая задачи мобильного приложения

class Tasks {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null

    @JsonManagedReference(value = "task-creator")
    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "creator_id")
    var creator: Users? = null
        private set

    @JsonFormat(pattern = "yyyy-MM-dd")
    var creation_date: Date? = null

    @JsonFormat(pattern = "yyyy-MM-dd")
    var date_of_update: Date? = null
    var description: String? = null

    @JsonFormat(pattern = "yyyy-MM-dd")
    var deadline: Date? = null
    var status: String? = null

    @JsonManagedReference(value = "task-executor")
    @ManyToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "executor_id")
    var executor: Users? = null
        private set

    @JsonManagedReference(value = "task-members")
    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(name = "tasks_user", joinColumns = [JoinColumn(name = "task_id")], inverseJoinColumns = [JoinColumn(name = "user_id")])
    var members: List<Users> = ArrayList()
        private set

    constructor(name: String?, creator: Users?, creation_date: Date?, date_of_update: Date?, description: String?, deadline: Date?, executor: Users?, status: String?) {
        this.name = name
        this.creator = creator
        this.creation_date = creation_date
        this.date_of_update = date_of_update
        this.description = description
        this.deadline = deadline
        this.executor = executor
        this.status = status
    }

    constructor(name: String?, creator: Users?, creation_date: Date?, description: String?, deadline: Date?, executor: Users?, status: String?, members: List<Users>) {
        this.name = name
        this.creator = creator
        this.creation_date = creation_date
        this.description = description
        this.deadline = deadline
        this.executor = executor
        this.status = status
        this.members = members
    }

    constructor()
}
