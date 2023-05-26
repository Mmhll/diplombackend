package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.pojo.MessageRequest
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.service.MessageService
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Message"])
open class MessageController(private val service: MessageService) {
    @PostMapping("/add_message")
    fun addMessage(@RequestBody request: MessageRequest): ResponseEntity<MessageResponse> {
        return service.addMessage(request)
    }

    @DeleteMapping("/delete_message")
    fun deleteMessage(@RequestParam("message_id") messageId: String, @RequestParam("chat_id") chatId: String): ResponseEntity<MessageResponse> {
        return service.deleteMessage(messageId.toLong(), chatId.toLong())
    }
}
