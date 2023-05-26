package com.mhl.mycompanybackend.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "message")
open class Message {
    @JvmField
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var text: String? = null

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    var created_at: Timestamp? = null
    var isIs_updated = false
        private set

    @JsonManagedReference(value = "message-user")
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "user_id")
    var user: Users? = null

    constructor(text: String?, user: Users?) {
        this.text = text
        this.user = user
    }

    constructor()

    fun setIs_updated(is_updated: Boolean) {
        isIs_updated = is_updated
    }
}
