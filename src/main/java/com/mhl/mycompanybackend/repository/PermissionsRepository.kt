package com.mhl.mycompanybackend.repository

import com.mhl.mycompanybackend.model.PermissionName
import com.mhl.mycompanybackend.model.Permissions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PermissionsRepository : JpaRepository<Permissions?, Long?> {
    fun findByName(name: PermissionName?): Optional<Permissions?>?
}
