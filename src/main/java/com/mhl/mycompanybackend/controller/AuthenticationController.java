package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.pojo.SignInRequest;
import com.mhl.mycompanybackend.pojo.SignupRequest;
import com.mhl.mycompanybackend.service.AuthenticationService;
import com.mhl.mycompanybackend.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    final
    AuthenticationService authenticationService;
    ErrorMessages messages;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, ErrorMessages messages) {
        this.authenticationService = authenticationService;
        this.messages = messages;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody SignInRequest signInRequest) {
        try {
            return authenticationService.authenticate(signInRequest);
        } catch (Exception e){
            return messages.badRequest();
        }

    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            return authenticationService.registerUser(signupRequest);
        } catch (Exception e){
            return messages.badRequest();
        }
    }
}
