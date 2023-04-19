package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.PermissionName;
import com.mhl.mycompanybackend.models.Permissions;
import com.mhl.mycompanybackend.models.Roles;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    final
    RolesRepository rolesRepository;
    final
    PermissionsRepository permissionsRepository;

    public RolesService(RolesRepository rolesRepository, PermissionsRepository permissionsRepository) {
        this.rolesRepository = rolesRepository;
        this.permissionsRepository = permissionsRepository;
    }

    public ResponseEntity<?> findAllRoles(){
        try {
            return ResponseEntity.ok().body(rolesRepository.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
        }
    }

    public ResponseEntity<?> createRole(String roleName, String permissions) {
        Permissions permission = permissionsRepository.findByName(
                        PermissionName.valueOf(permissions))
                .orElseThrow(
                        () -> new RuntimeException("Permission not found")
                );

            Roles role = new Roles(roleName, permission);
        try {
            rolesRepository.save(role);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Role exists"));
        }
        return ResponseEntity.ok().body(new MessageResponse("Role has been created"));
    }

    public Roles findRoleByName(String roleName) {
        return rolesRepository.findRolesByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public ResponseEntity<MessageResponse> editRoleName(String roleName){
        try {
            rolesRepository.updateRoleName(roleName);
            return ResponseEntity.ok().body(new MessageResponse("Role was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Role not found"));
        }
    }

    public ResponseEntity<MessageResponse> editRolePermission(String roleName, PermissionName permissionName){
        try {
            rolesRepository.updateRolePermission(permissionName,roleName);
            return ResponseEntity.ok().body(new MessageResponse("Permissions was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Role not found"));
        }
    }
}
