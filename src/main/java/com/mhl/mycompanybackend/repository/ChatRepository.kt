package com.mhl.mycompanybackend.repository

import com.mhl.mycompanybackend.model.Chat
import com.mhl.mycompanybackend.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp

@Repository
interface ChatRepository : JpaRepository<Chat?, Long?> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE chat SET title = ?2 WHERE id = ?1")
    fun updateChatByName(id: Long?, title: String?)
    fun findChatsByUsersOrderByUpdatedAtDescCreatedAtDesc(user: Users?): List<Chat?>?

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM mhl.public.chat_user WHERE mhl.public.chat_user.chat_id = ?1 AND mhl.public.chat_user.user_id = ?2")
    fun deleteUserFromChat(chatId: Long?, userId: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO chat_user(chat_id, user_id) VALUES (?1, ?2)")
    fun addUserToChat(chatId: Long?, userId: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE chat SET " +
            "updated_at = ?1 WHERE id = ?2")
    fun updateChatDate(datetime: Timestamp?, chat_id: Long?)
}
