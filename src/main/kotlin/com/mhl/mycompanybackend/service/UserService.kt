package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.model.Users
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.pojo.UserRequest
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest
import com.mhl.mycompanybackend.repository.UserDataRepository
import com.mhl.mycompanybackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val userDataRepository: UserDataRepository, private val encoder: PasswordEncoder) {
    fun getUserById(id: Long): Users {
        return userRepository.findById(id).orElseThrow { RuntimeException("User not found") }!!
    }

    fun setUserData(request: UserRequest): ResponseEntity<MessageResponse> {
        return try {
            userDataRepository.updateUser(
                    request.firstname,
                    request.lastname,
                    request.middlename,
                    request.phone_number,
                    request.id
            )
            userRepository.updateUserEmail(request.id, request.email)
            ResponseEntity.ok().body(MessageResponse("User was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found"))
        }
    }

    fun setUserPassword(request: UsernameAndPasswordRequest): ResponseEntity<MessageResponse> {
        return try {
            userRepository.updateUserPassword(request.id, encoder.encode(request.password))
            ResponseEntity.ok().body(MessageResponse("Password was changed"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found"))
        }
    }

    fun findAllUsers(): ResponseEntity<List<Users?>> {
        return ResponseEntity.ok().body(userRepository.findAll())
    }

    fun deleteUser(email: String?): ResponseEntity<*> {
        val user = userRepository.findByEmail(email)?.orElseThrow { RuntimeException("User not found") }
        return try {
            userRepository.deleteFromUserRoles(user?.id)
            userRepository.delete(user!!)
            ResponseEntity.ok().body<MessageResponse>(MessageResponse("User was successfully deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body<MessageResponse>(MessageResponse(e.message!!))
        }
    }
}
