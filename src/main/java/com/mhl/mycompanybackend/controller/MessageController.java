package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.pojo.MessageChatRequest;
import com.mhl.mycompanybackend.pojo.MessageRequest;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api( tags = "Message")
public class MessageController {
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/add_message")
    public ResponseEntity<MessageResponse> addMessage(@RequestBody MessageRequest request){
        return service.addMessage(request);
    }

    @DeleteMapping("/delete_message")
    public ResponseEntity<MessageResponse> deleteMessage(@RequestBody MessageChatRequest request){
        return service.deleteMessage(request);
    }
}
