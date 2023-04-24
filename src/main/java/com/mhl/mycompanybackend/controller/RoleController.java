package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.pojo.RoleRequest;
import com.mhl.mycompanybackend.pojo.RolenameRequest;
import com.mhl.mycompanybackend.service.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
    final
    RolesService rolesService;

    public RoleController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/get_all")
    ResponseEntity<?> getAllRoles() {
        return rolesService.findAllRoles();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/add_role")
    ResponseEntity<?> addRole(@RequestBody RoleRequest request) {
        return rolesService.createRole(request.getRolename(), request.getPermission());
    }


    @PostMapping("/find_role_by_name")
    ResponseEntity<?> findRoleByName(@RequestBody RolenameRequest request) {
        return ResponseEntity.ok().body(rolesService.findRoleByName(request.getRolename()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/edit_role_name")
    ResponseEntity<?> editRoleName(@RequestBody RolenameRequest request) {
        return rolesService.editRoleName(request.getRolename());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/edit_role_permission")
    ResponseEntity<?> editRolePermission(@RequestBody RoleRequest request){
        return rolesService.editRolePermission(request.getRolename(), PermissionName.valueOf(request.getPermission()));
    }
}
