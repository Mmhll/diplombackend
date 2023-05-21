package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api( tags = "Chat")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class ChatController {
    final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    
    @PostMapping("/create")
    public ResponseEntity<?> createChat(@RequestBody CreateChatRequest createChatRequest){
        return service.addChat(createChatRequest);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITUSER', 'MODERATOR')")
    @PutMapping("/update_chat_name")
    public ResponseEntity<MessageResponse> updateChatName(@RequestBody UpdateChatNameRequest request){
        return service.updateChatName(request);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_chat")
    public ResponseEntity<MessageResponse> deleteChat(@RequestParam("id") String id){
        return service.deleteChat(Long.parseLong(id));
    }

    
    @GetMapping("/get_chats")
    public ResponseEntity<?> getChats(@RequestParam String id){
        return service.getChats(id);
    }

    @GetMapping("/get_chat")
    public ResponseEntity<?> getChat(@RequestParam String id) {return service.getChat(id);}

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_chat_member")
    public ResponseEntity<MessageResponse> deleteChatMember(@RequestParam("user_id") String userId, @RequestParam("chat_id") String chatId){
        return service.deleteChatMember(Long.parseLong(userId), Long.parseLong(chatId));
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITUSER', 'MODERATOR')")
    @PostMapping("/add_chat_member")
    public ResponseEntity<MessageResponse> addChatMember(@RequestBody UserIdChatIdRequest request){
        return service.addChatMember(request);
    }
}
