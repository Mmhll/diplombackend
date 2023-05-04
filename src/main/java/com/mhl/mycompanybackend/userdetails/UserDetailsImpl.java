package com.mhl.mycompanybackend.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhl.mycompanybackend.model.Roles;
import com.mhl.mycompanybackend.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String email;
    @JsonIgnore
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final List<Roles> roles;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities, List<Roles> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.roles = roles;
    }

    public static UserDetailsImpl build(Users users) {
        List<GrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getPermissions().getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                users.getId(),
                users.getUsername(),
                users.getEmail(),
                users.getPassword(),
                authorities, users.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDetailsImpl other = (UserDetailsImpl) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

}

