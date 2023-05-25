package com.mhl.mycompanybackend.utils

import com.mhl.mycompanybackend.pojo.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ErrorMessages {
    fun badRequest(): ResponseEntity<MessageResponse> {
        return ResponseEntity.badRequest().body(MessageResponse("Bad request"))
    }
}
