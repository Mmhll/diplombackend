package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.model.Permissions;
import com.mhl.mycompanybackend.model.Roles;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<MessageResponse> editRole(Long id, String roleName, String permission){
        try {
            Long permissionId = rolesRepository.getPermissions(permission);
            rolesRepository.updateRole(id, roleName, permissionId);
            return ResponseEntity.ok().body(new MessageResponse("Role was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Role not found"));
        }
    }
}
