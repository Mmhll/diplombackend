package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE chat SET title = ?2 WHERE id = ?1")
    void updateChatByName(Long id, String title);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SELECT * FROM chat WHERE (SELECT chat_id FROM chat_user WHERE user_id = ?1) = chat.id;")
    List<Chat> getChatsByUserId(Long id);
}
