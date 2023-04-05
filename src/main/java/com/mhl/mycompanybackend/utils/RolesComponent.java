package com.mhl.mycompanybackend.utils;

import com.mhl.mycompanybackend.models.PermissionName;
import com.mhl.mycompanybackend.models.Permissions;
import com.mhl.mycompanybackend.models.Roles;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesComponent implements ApplicationRunner {

    @Autowired
    private PermissionsRepository permissionsRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public void run(ApplicationArguments args) {
        var data = permissionsRepository.findAll();
        if (data.isEmpty()){
            Permissions userRole = new Permissions(PermissionName.ROLE_USER),
                    editUserRole = new Permissions(PermissionName.ROLE_EDITUSER),
                    adminRole = new Permissions(PermissionName.ROLE_ADMIN),
                    moderatorRole = new Permissions(PermissionName.ROLE_MODERATOR);
            permissionsRepository.save(userRole);
            permissionsRepository.save(editUserRole);
            permissionsRepository.save(moderatorRole);
            permissionsRepository.save(adminRole);
        }
        data = permissionsRepository.findAll();
        var roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            rolesRepository.save(new Roles("USER", data.get(0)));
            rolesRepository.save(new Roles("ADMIN", data.get(3)));
        }
    }
}