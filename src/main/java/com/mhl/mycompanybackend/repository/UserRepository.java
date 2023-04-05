package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET " +
            "email = ?2 "+
            "WHERE id = ?1")
    void updateUserEmailAndUsername(Long id, String email);

    @Query(nativeQuery = true, value = "UPDATE users SET" +
            " password = ?2 " +
            "WHERE id = ?1")
    void updateUserPassword(Long id, String password);

}
