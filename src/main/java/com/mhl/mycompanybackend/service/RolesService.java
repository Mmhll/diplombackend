package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.PermissionName;
import com.mhl.mycompanybackend.models.Permissions;
import com.mhl.mycompanybackend.models.Roles;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PermissionsRepository permissionsRepository;
    public void createRole(String roleName, PermissionName permissions){
        Permissions permission = permissionsRepository.findByName(permissions).orElseThrow();
        Roles role = new Roles(roleName,permission);
        rolesRepository.save(role);
    }
    public Roles findRoleByName(String roleName){
        return rolesRepository.findRolesByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
