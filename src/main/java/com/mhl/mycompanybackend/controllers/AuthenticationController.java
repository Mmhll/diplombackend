package com.mhl.mycompanybackend.controllers;

import com.mhl.mycompanybackend.pojo.UserAndPasswordRequest;
import com.mhl.mycompanybackend.pojo.SignupRequest;
import com.mhl.mycompanybackend.service.AuthenticationService;
import com.mhl.mycompanybackend.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    ErrorMessages messages;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody UserAndPasswordRequest userAndPasswordRequest) {
        try {
            return authenticationService.authenticate(userAndPasswordRequest);
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
