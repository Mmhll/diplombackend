package com.mhl.mycompanybackend.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {
    @Id
    private Long id;
    private String text;
    private Timestamp created_at;
    private boolean is_updated;
    @ManyToOne

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
