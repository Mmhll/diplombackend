package com.mhl.mycompanybackend.userdetails

import com.mhl.mycompanybackend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    var userRepository: UserRepository? = null
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.findByUsername(username)?.orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }
        return UserDetailsImpl.build(user!!)
    }

    @Transactional
    @Throws(UsernameNotFoundException::class)
    open fun loadUserByEmail(email: String): UserDetails {
        val user = userRepository!!.findByEmail(email)?.orElseThrow { UsernameNotFoundException("User Not Found with email: $email") }
        return UserDetailsImpl.build(user!!)
    }
}
