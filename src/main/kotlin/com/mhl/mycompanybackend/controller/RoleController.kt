package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.model.PermissionName
import com.mhl.mycompanybackend.pojo.RoleRequest
import com.mhl.mycompanybackend.pojo.RolenameRequest
import com.mhl.mycompanybackend.service.RolesService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Roles"])
@ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
class RoleController(private val rolesService: RolesService) {
    @get:GetMapping("/get_all")
    val allRoles: ResponseEntity<*>
        get() = rolesService.findAllRoles()

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/add_role")
    fun addRole(@RequestBody request: RoleRequest): ResponseEntity<*> {
        return rolesService.createRole(request.rolename, request.permission)
    }

    @PostMapping("/find_role_by_name")
    fun findRoleByName(@RequestBody request: RolenameRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(rolesService.findRoleByName(request.rolename))
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/edit_role_name")
    fun editRoleName(@RequestBody request: RolenameRequest): ResponseEntity<*> {
        return rolesService.editRoleName(request.rolename)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/edit_role_permission")
    fun editRolePermission(@RequestBody request: RoleRequest): ResponseEntity<*> {
        return rolesService.editRolePermission(request.rolename, PermissionName.valueOf(request.permission!!))
    }
}
