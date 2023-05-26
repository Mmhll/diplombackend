package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.pojo.CreateChatRequest
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.pojo.UpdateChatNameRequest
import com.mhl.mycompanybackend.pojo.UserIdChatIdRequest
import com.mhl.mycompanybackend.service.ChatService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Chat"])
@ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
open class ChatController(val service: ChatService) {
    @PostMapping("/create")
    fun createChat(@RequestBody createChatRequest: CreateChatRequest?): ResponseEntity<*> {
        return service.addChat(createChatRequest!!)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITUSER', 'MODERATOR')")
    @PutMapping("/update_chat_name")
    fun updateChatName(@RequestBody request: UpdateChatNameRequest?): ResponseEntity<MessageResponse> {
        return service.updateChatName(request!!)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_chat")
    fun deleteChat(@RequestParam("id") id: String): ResponseEntity<MessageResponse> {
        return service.deleteChat(id.toLong())
    }

    @GetMapping("/get_chats")
    fun getChats(@RequestParam id: String?): ResponseEntity<*> {
        return service.getChats(id!!)
    }

    @GetMapping("/get_chat")
    fun getChat(@RequestParam id: String?): ResponseEntity<*> {
        return service.getChat(id!!)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_chat_member")
    fun deleteChatMember(@RequestParam("user_id") userId: String, @RequestParam("chat_id") chatId: String): ResponseEntity<MessageResponse> {
        return service.deleteChatMember(userId.toLong(), chatId.toLong())
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITUSER', 'MODERATOR')")
    @PostMapping("/add_chat_member")
    fun addChatMember(@RequestBody request: UserIdChatIdRequest?): ResponseEntity<MessageResponse> {
        return service.addChatMember(request!!)
    }
}
