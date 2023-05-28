package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT * FROM roles WHERE roles.role_name = ?1")
    Optional<Roles> findRolesByRoleName(String roleName);
    @Query(nativeQuery = true, value = "UPDATE roles SET role_name = ?2 WHERE id = ?1")
    @Modifying
    void updateRoleName(Long id, String roleName);
    @Query(nativeQuery = true, value = "UPDATE roles SET permission_id = ?1 WHERE role_name = ?2")
    @Modifying
    void updateRolePermission(PermissionName permission, String roleName);
}
