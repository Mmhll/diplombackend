package com.mhl.mycompanybackend.controllers;

import com.mhl.mycompanybackend.pojo.UserRequest;
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

    @PostMapping("/update_user_self")
    public ResponseEntity<?> updateUserSelf(@RequestBody UserRequest request){
        return userService.setUserData(request);
    }
}
