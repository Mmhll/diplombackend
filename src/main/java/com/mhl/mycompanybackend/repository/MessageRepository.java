package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO mhl.public.chat_message(chat_id, message_id) VALUES (?1, ?2)")
    void addMessageToChat(Long chatId, Long messageId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM mhl.public.chat_message WHERE mhl.public.chat_message.chat_id = ?1 AND mhl.public.chat_message.message_id = ?2")
    void deleteMessageChat(Long chatId, Long messageId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT id FROM mhl.public.message ORDER BY id DESC")
    Long findLast();
}
