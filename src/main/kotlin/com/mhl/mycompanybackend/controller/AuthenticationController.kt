package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.pojo.SignInRequest
import com.mhl.mycompanybackend.pojo.SignupRequest
import com.mhl.mycompanybackend.service.AuthenticationService
import com.mhl.mycompanybackend.utils.ErrorMessages
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Auth"])
class AuthenticationController {
    val authenticationService: AuthenticationService
    var messages: ErrorMessages? = null

    constructor(authenticationService: AuthenticationService) {
        this.authenticationService = authenticationService
    }

    @Autowired
    constructor(authenticationService: AuthenticationService, messages: ErrorMessages?) {
        this.authenticationService = authenticationService
        this.messages = messages
    }

    @PostMapping("/signin")
    fun authUser(@RequestBody signInRequest: SignInRequest?): ResponseEntity<*> {
        return try {
            authenticationService.authenticate(signInRequest!!)
        } catch (e: Exception) {
            messages!!.badRequest()
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITUSER', 'MODERATOR', 'USER')")
    @PostMapping("/signup")
    fun registerUser(@RequestBody signupRequest: SignupRequest?): ResponseEntity<*> {
        return try {
            authenticationService.registerUser(signupRequest!!)
        } catch (e: Exception) {
            messages!!.badRequest()
        }
    }
}
