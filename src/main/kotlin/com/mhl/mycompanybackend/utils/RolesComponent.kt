package com.mhl.mycompanybackend.utils

import com.mhl.mycompanybackend.model.PermissionName
import com.mhl.mycompanybackend.model.Permissions
import com.mhl.mycompanybackend.model.Roles
import com.mhl.mycompanybackend.repository.PermissionsRepository
import com.mhl.mycompanybackend.repository.RolesRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class RolesComponent(private val permissionsRepository: PermissionsRepository, private val rolesRepository: RolesRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        var data = permissionsRepository.findAll()
        if (data.isEmpty()) {
            val userRole = Permissions(PermissionName.ROLE_USER)
            val editUserRole = Permissions(PermissionName.ROLE_EDITUSER)
            val adminRole = Permissions(PermissionName.ROLE_ADMIN)
            val moderatorRole = Permissions(PermissionName.ROLE_MODERATOR)
            permissionsRepository.save(userRole)
            permissionsRepository.save(editUserRole)
            permissionsRepository.save(moderatorRole)
            permissionsRepository.save(adminRole)
        }
        data = permissionsRepository.findAll()
        val roles = rolesRepository.findAll()
        if (roles.isEmpty()) {
            rolesRepository.save(Roles("USER", data[0]))
            rolesRepository.save(Roles("ADMIN", data[3]))
        }
    }
}