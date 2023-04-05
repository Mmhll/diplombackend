package com.mhl.mycompanybackend.models;

import javax.persistence.*;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "role_name")
})
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    private String RoleName;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permissions permissions;

    public Roles() {
    }

    public Roles(String roleName, Permissions permissions) {
        RoleName = roleName;
        this.permissions = permissions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getRole_name() {
        return RoleName;
    }

    public void setRole_name(String role_name) {
        this.RoleName = role_name;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
}
