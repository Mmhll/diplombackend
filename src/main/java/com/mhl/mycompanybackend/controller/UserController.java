package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.pojo.OneParamStringRequest;
import com.mhl.mycompanybackend.pojo.UserIdAndRoleIdRequest;
import com.mhl.mycompanybackend.pojo.UserRequest;
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest;
import com.mhl.mycompanybackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api( tags = "User")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    @PutMapping("/update_user_data")
    public ResponseEntity<?> updateUserData(@RequestBody UserRequest request) {
        return userService.setUserData(request);
    }

    
    @PutMapping("/update_user_password")
    public ResponseEntity<?> updateUserPassword(@RequestBody UsernameAndPasswordRequest request) {
        return userService.setUserPassword(request);
    }

    
    @GetMapping("/find_all")
    public ResponseEntity<?> findAllUsers() {
        return userService.findAllUsers();
    }

    
    @DeleteMapping("/delete_user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam("id") String id){
        return userService.deleteUser(Long.parseLong(id));
    }

    @PutMapping("update_user_role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@RequestBody UserIdAndRoleIdRequest request){
        return userService.updateUserRole(request);
    }
}
