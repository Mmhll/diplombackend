package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.configs.jwt.JwtUtils
import com.mhl.mycompanybackend.model.Roles
import com.mhl.mycompanybackend.model.UserData
import com.mhl.mycompanybackend.model.Users
import com.mhl.mycompanybackend.pojo.JwtResponse
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.pojo.SignInRequest
import com.mhl.mycompanybackend.pojo.SignupRequest
import com.mhl.mycompanybackend.repository.PermissionsRepository
import com.mhl.mycompanybackend.repository.RolesRepository
import com.mhl.mycompanybackend.repository.UserDataRepository
import com.mhl.mycompanybackend.repository.UserRepository
import com.mhl.mycompanybackend.userdetails.UserDetailsImpl
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class AuthenticationService(val authenticationManager: AuthenticationManager, val userRepository: UserRepository, val userDataRepository: UserDataRepository, val permissionsRepository: PermissionsRepository, val rolesRepository: RolesRepository, val passwordEncoder: PasswordEncoder, val jwtUtils: JwtUtils) {
    fun authenticate(signInRequest: SignInRequest): ResponseEntity<JwtResponse> {
        val authentication = authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(
                        signInRequest.username,
                        signInRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles: MutableList<String?> = ArrayList()
        userDetails.roles.forEach(Consumer { oneRole: Roles? -> roles.add(oneRole!!.role_name) })
        return ResponseEntity.ok(JwtResponse(
                jwt
        )
        )
    }

    fun registerUser(signupRequest: SignupRequest): ResponseEntity<*> {
        val username = signupRequest.email!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Username or email exists"))
        }
        val userData = UserData(
                signupRequest.firstname,
                signupRequest.lastname,
                signupRequest.middlename,
                signupRequest.phone_number)
        userDataRepository.save(userData)
        userDataRepository.findById(userData.id)
        val user = Users(username,
                signupRequest.email,
                passwordEncoder.encode(signupRequest.password),
                userData)
        val roles: MutableList<Roles?> = ArrayList()
        val userRole = rolesRepository
                .findRolesByRoleName("USER")
                .orElseThrow { RuntimeException("Error, Role USER is not found") }
        roles.add(userRole)
        user.setRoles(roles)
        userRepository.save(user)
        return ResponseEntity.ok().body(MessageResponse("User was created"))
    }
}
