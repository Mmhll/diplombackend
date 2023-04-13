package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.Chat;
import com.mhl.mycompanybackend.pojo.ChatRequest;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.repository.ChatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService service;
    public ChatService(ChatRepository chatRepository, UserService service) {
        this.chatRepository = chatRepository;
        this.service = service;
    }
    public ResponseEntity<?> addChat(ChatRequest request){
        try {
            Chat chat = new Chat(request.getName());
            chat.setUsers(request.getMembers());
            chatRepository.save(chat);
            return ResponseEntity.ok().body(new MessageResponse("Chat was created"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
