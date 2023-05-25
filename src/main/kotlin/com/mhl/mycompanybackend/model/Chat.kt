package com.mhl.mycompanybackend.model

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "chat")
class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var title: String? = null

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    var createdAt: Timestamp? = null

    @Column(name = "updated_at")
    var updatedAt: Timestamp? = null

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(name = "chat_message", joinColumns = [JoinColumn(name = "chat_id")], inverseJoinColumns = [JoinColumn(name = "message_id")])
    var messages: List<Message> = ArrayList()

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(name = "chat_user", joinColumns = [JoinColumn(name = "chat_id")], inverseJoinColumns = [JoinColumn(name = "user_id")])
    var users: List<Users> = ArrayList()

    constructor()
    constructor(title: String?) {
        this.title = title
    }
}
