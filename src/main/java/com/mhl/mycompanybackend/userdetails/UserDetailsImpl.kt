package com.mhl.mycompanybackend.userdetails

import com.fasterxml.jackson.annotation.JsonIgnore
import com.mhl.mycompanybackend.model.Roles
import com.mhl.mycompanybackend.model.UserData
import com.mhl.mycompanybackend.model.Users
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serial
import java.util.stream.Collectors

class UserDetailsImpl(
    val id: Long?, private val username: String, val email: String, @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>, val roles: List<Roles>, val userData: UserData
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as UserDetailsImpl
        return if (id == null) {
            other.id == null
        } else id == other.id
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
        fun build(users: Users): UserDetailsImpl {
            val authorities = users.roles.stream()
                .map { role: Roles -> SimpleGrantedAuthority(role.permissions!!.name!!.name) }
                .collect(Collectors.toList())
            println(users.userData!!.firstname)
            return UserDetailsImpl(
                users.id,
                users.username!!,
                users.email!!,
                users.password!!,
                authorities, users.roles, users.userData!!
            )

//
        }
    }
}
