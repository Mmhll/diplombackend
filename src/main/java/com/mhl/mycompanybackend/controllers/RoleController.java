package com.mhl.mycompanybackend.controllers;

import com.mhl.mycompanybackend.models.PermissionName;
import com.mhl.mycompanybackend.pojo.RoleRequest;
import com.mhl.mycompanybackend.pojo.RolenameRequest;
import com.mhl.mycompanybackend.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
    @Autowired
    RolesService rolesService;

    @GetMapping("/get_all")
    ResponseEntity<?> getAllRoles() {
        return rolesService.findAllRoles();
    }

    @PostMapping("/add_role")
    ResponseEntity<?> addRole(@RequestBody RoleRequest request) {
        return rolesService.createRole(request.getRolename(), request.getPermission());
    }

    @PostMapping("/find_role_by_name")
    ResponseEntity<?> findRoleByName(@RequestBody RolenameRequest request) {
        return ResponseEntity.ok().body(rolesService.findRoleByName(request.getRolename()));
    }

    @PostMapping("/edit_role_name")
    ResponseEntity<?> editRoleName(@RequestBody RolenameRequest request) {
        return rolesService.editRoleName(request.getRolename());
    }

    @PostMapping("/edit_role_permission")
    ResponseEntity<?> editRolePermission(@RequestBody RoleRequest request){
        return rolesService.editRolePermission(request.getRolename(), PermissionName.valueOf(request.getPermission()));
    }
}
