package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.model.Permissions;
import com.mhl.mycompanybackend.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT * FROM roles WHERE roles.role_name = ?1")
    Optional<Roles> findRolesByRoleName(String roleName);
    @Query(nativeQuery = true, value = "UPDATE roles SET role_name = ?2, permission_id = ?3 WHERE id = ?1")
    @Modifying
    @Transactional
    void updateRole(Long id, String roleName, Long permission_id);

    @Query(nativeQuery = true, value = "SELECT id FROM permissions WHERE name = ?1")
    Long getPermissions(String permission);
}
