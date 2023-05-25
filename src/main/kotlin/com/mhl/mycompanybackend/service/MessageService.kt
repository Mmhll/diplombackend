package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.model.Message
import com.mhl.mycompanybackend.pojo.MessageRequest
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.repository.ChatRepository
import com.mhl.mycompanybackend.repository.MessageRepository
import com.mhl.mycompanybackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class MessageService(private val messageRepository: MessageRepository, private val userRepository: UserRepository, private val chatRepository: ChatRepository) {
    fun addMessage(request: MessageRequest): ResponseEntity<MessageResponse> {
        val user = userRepository.findById(request.user_id!!).orElseThrow { RuntimeException("User not found") }!!
        return try {
            val message = Message(request.message, user)
            messageRepository.save(message)
            messageRepository.addMessageToChat(request.chat_id, message.id)
            chatRepository.updateChatDate(Timestamp.from(Instant.now()), request.chat_id)
            ResponseEntity.ok().body<MessageResponse>(MessageResponse("Message was saved"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body<MessageResponse>(MessageResponse("Something went wrong"))
        }
    }

    fun deleteMessage(messageId: Long, chatId: Long?): ResponseEntity<MessageResponse> {
        return try {
            val message = messageRepository.findById(messageId).orElseThrow { RuntimeException("Message not found") }!!
            messageRepository.delete(message)
            messageRepository.deleteMessageChat(chatId, messageId)
            ResponseEntity.ok().body(MessageResponse("Message was deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Something went wrong"))
        }
    }
}
