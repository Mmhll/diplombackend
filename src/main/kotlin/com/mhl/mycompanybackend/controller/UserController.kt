package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.pojo.UserRequest
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest
import com.mhl.mycompanybackend.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["User"])
@ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
open class UserController(val userService: UserService) {
    @PutMapping("/update_user_data")
    fun updateUserData(@RequestBody request: UserRequest?): ResponseEntity<*> {
        return userService.setUserData(request!!)
    }

    @PutMapping("/update_user_password")
    fun updateUserPassword(@RequestBody request: UsernameAndPasswordRequest?): ResponseEntity<*> {
        return userService.setUserPassword(request!!)
    }

    @GetMapping("/find_all")
    fun findAllUsers(): ResponseEntity<*> {
        return userService.findAllUsers()
    }

    @DeleteMapping("/delete_user")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(@RequestParam("email") email: String?): ResponseEntity<*> {
        return userService.deleteUser(email)
    }
}
