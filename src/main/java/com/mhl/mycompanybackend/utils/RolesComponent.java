package com.mhl.mycompanybackend.utils;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.model.Permissions;
import com.mhl.mycompanybackend.model.Roles;
import com.mhl.mycompanybackend.pojo.SignupRequest;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import com.mhl.mycompanybackend.service.AuthenticationService;
import com.mhl.mycompanybackend.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesComponent implements ApplicationRunner {

    private final PermissionsRepository permissionsRepository;
    private final RolesRepository rolesRepository;
    private final AuthenticationService userService;
    private final UserRepository userRepository;

    public RolesComponent(PermissionsRepository permissionsRepository, RolesRepository rolesRepository, AuthenticationService service, UserRepository userRepository) {
        this.permissionsRepository = permissionsRepository;
        this.rolesRepository = rolesRepository;
        this.userService = service;
        this.userRepository = userRepository;
    }

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
        var users = userRepository.findAll();
        if (users.isEmpty()){
            userService.registerUser(new SignupRequest(
                "admin@admin.ru",
                "admin",
                "admin",
                "admin",
                "123"
            ));
        }
    }
}