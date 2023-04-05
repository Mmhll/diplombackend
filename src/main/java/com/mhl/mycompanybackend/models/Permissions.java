package com.mhl.mycompanybackend.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permissions")
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PermissionName name;

    @OneToMany(mappedBy = "permissions")
    private List<Roles> roles;

    public Permissions() {}

    public Permissions(PermissionName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PermissionName getName() {
        return name;
    }

    public void setName(PermissionName name) {
        this.name = name;
    }

}