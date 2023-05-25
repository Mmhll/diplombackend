package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.model.Chat
import com.mhl.mycompanybackend.model.Users
import com.mhl.mycompanybackend.pojo.CreateChatRequest
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.pojo.UpdateChatNameRequest
import com.mhl.mycompanybackend.pojo.UserIdChatIdRequest
import com.mhl.mycompanybackend.repository.ChatRepository
import com.mhl.mycompanybackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class ChatService(private val chatRepository: ChatRepository, private val service: UserService, private val userRepository: UserRepository) {
    fun addChat(request: CreateChatRequest): ResponseEntity<*> {
        return try {
            if (request.name!!.isEmpty() || request.name == null) {
                return ResponseEntity.badRequest().body(MessageResponse("Chat name must not be null"))
            }
            if (request.members.isEmpty()) {
                return ResponseEntity.badRequest().body(MessageResponse("Chat members must not be null"))
            }
            val chat = Chat(request.name)
            val users = ArrayList<Users>()
            request.members.forEach(Consumer { user: Long -> users.add(service.getUserById(user)) })
            chat.users = users
            chatRepository.save(chat)
            ResponseEntity.ok().body(MessageResponse("Chat was created"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse(e.message!!))
        }
    }

    fun updateChatName(request: UpdateChatNameRequest): ResponseEntity<MessageResponse> {
        return try {
            chatRepository.updateChatByName(request.id, request.name)
            ResponseEntity.ok().body(MessageResponse("Chat name was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Chat not found or something went wrong"))
        }
    }

    fun deleteChat(id: Long): ResponseEntity<MessageResponse> {
        return try {
            chatRepository.deleteById(id)
            ResponseEntity.ok().body(MessageResponse("Chat was deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Chat not found or something went wrong"))
        }
    }

    fun getChats(id: String): ResponseEntity<*> {
        /*try {*/
        return ResponseEntity.ok().body(chatRepository.findChatsByUsersOrderByUpdatedAtDescCreatedAtDesc(userRepository.findById(id.toLong()).orElseThrow { RuntimeException("user not found") }))
        /*} catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("No chats found"));
        }*/
    }

    fun deleteChatMember(userId: Long?, chatId: Long?): ResponseEntity<MessageResponse> {
        return try {
            chatRepository.deleteUserFromChat(chatId, userId)
            ResponseEntity.ok().body(MessageResponse("User was deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found in this chat or something went wrong"))
        }
    }

    fun addChatMember(request: UserIdChatIdRequest): ResponseEntity<MessageResponse> {
        return try {
            chatRepository.addUserToChat(request.chat_id, request.user_id)
            ResponseEntity.ok().body(MessageResponse("User was added"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found or something went wrong"))
        }
    }

    fun getChat(id: String): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(chatRepository.findById(id.toLong()).get())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Something went wrong or chat doesn't exists"))
        }
    }
}
