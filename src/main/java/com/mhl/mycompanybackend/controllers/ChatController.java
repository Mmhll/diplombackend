package com.mhl.mycompanybackend.controllers;

import com.mhl.mycompanybackend.pojo.CreateChatRequest;
import com.mhl.mycompanybackend.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChatController {
    final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createChat(@RequestBody CreateChatRequest createChatRequest){
        return service.addChat(createChatRequest);
    }
}
