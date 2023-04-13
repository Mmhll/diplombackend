package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    /*@Transactional
    @Modifying
    @Query(nativeQuery = true, value = )*/
}
