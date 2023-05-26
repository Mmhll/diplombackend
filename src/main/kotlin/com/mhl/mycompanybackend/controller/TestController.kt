package com.mhl.mycompanybackend.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Test"])
open class TestController {
    @GetMapping("/all")
    fun allAccess(): String {
        return "public API"
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun userAccess(): String {
        return "user API"
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    fun moderatorAccess(): String {
        return "moderator API"
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        return "admin API"
    }
}
