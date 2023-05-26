package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.model.PermissionName
import com.mhl.mycompanybackend.model.Roles
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.repository.PermissionsRepository
import com.mhl.mycompanybackend.repository.RolesRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class RolesService(val rolesRepository: RolesRepository, val permissionsRepository: PermissionsRepository) {
    fun findAllRoles(): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(rolesRepository.findAll())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Something went wrong"))
        }
    }

    fun createRole(roleName: String, permissions: String): ResponseEntity<*> {
        val permission = permissionsRepository.findByName(
                PermissionName.valueOf(permissions!!))?.orElseThrow { RuntimeException("Permission not found") }
        val role = Roles(roleName, permission)
        try {
            rolesRepository.save(role)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(MessageResponse("Role exists"))
        }
        return ResponseEntity.ok().body(MessageResponse("Role has been created"))
    }

    fun findRoleByName(roleName: String?): Roles? {
        return rolesRepository.findRolesByRoleName(roleName)?.orElseThrow { RuntimeException("Role not found") }
    }

    fun editRoleName(roleName: String?): ResponseEntity<MessageResponse> {
        return try {
            rolesRepository.updateRoleName(roleName)
            ResponseEntity.ok().body(MessageResponse("Role was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Role not found"))
        }
    }

    fun editRolePermission(roleName: String?, permissionName: PermissionName?): ResponseEntity<MessageResponse> {
        return try {
            rolesRepository.updateRolePermission(permissionName, roleName)
            ResponseEntity.ok().body(MessageResponse("Permissions was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Role not found"))
        }
    }
}
