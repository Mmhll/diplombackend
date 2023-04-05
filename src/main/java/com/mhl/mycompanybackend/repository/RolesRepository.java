package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT * FROM roles WHERE roles.role_name = ?1")
    Optional<Roles> findRolesByRoleName(String roleName);
}
