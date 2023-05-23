package com.mhl.mycompanybackend.repository

import com.mhl.mycompanybackend.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface UserRepository : JpaRepository<Users?, Long?> {
    //
    fun findByUsername(username: String?): Optional<Users?>?
    fun findByEmail(email: String?): Optional<Users?>?
    fun existsByUsername(username: String?): Boolean?
    fun existsByEmail(email: String?): Boolean?

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET " +
            "email = ?2 " +
            "WHERE id = ?1")
    fun updateUserEmail(id: Long?, email: String?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_roles WHERE user_id = ?1")
    fun deleteFromUserRoles(id: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET" +
            " password = ?2 " +
            "WHERE id = ?1")
    fun updateUserPassword(id: Long?, password: String?)
}
