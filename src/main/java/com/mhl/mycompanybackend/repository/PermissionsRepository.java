package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.PermissionName;
import com.mhl.mycompanybackend.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    Optional<Permissions> findByName(PermissionName name);
}
