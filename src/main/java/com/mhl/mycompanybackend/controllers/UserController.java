package com.mhl.mycompanybackend.controllers;

import com.mhl.mycompanybackend.pojo.UserRequest;
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest;
import com.mhl.mycompanybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/update_user_data")
    public ResponseEntity<?> updateUserSelf(@RequestBody UserRequest request) {
        return userService.setUserData(request);
    }

    @PostMapping("/update_user_password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UsernameAndPasswordRequest request) {
        return userService.setUserPassword(request);
    }

    @GetMapping("find_all")
    public ResponseEntity<?> findAllUsers() {
        return userService.findAllUsers();
    }
}
