package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.Chat;
import com.mhl.mycompanybackend.models.Users;
import com.mhl.mycompanybackend.pojo.CreateChatRequest;
import com.mhl.mycompanybackend.pojo.IdRequest;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.UpdateChatNameRequest;
import com.mhl.mycompanybackend.repository.ChatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService service;
    public ChatService(ChatRepository chatRepository, UserService service) {
        this.chatRepository = chatRepository;
        this.service = service;
    }
    public ResponseEntity<?> addChat(CreateChatRequest request){
        try {
            if (request.getName().isEmpty() || request.getName() == null){
                return ResponseEntity.badRequest().body(new MessageResponse("Chat name must not be null"));
            }
            if (request.getMembers().isEmpty()){
                return ResponseEntity.badRequest().body(new MessageResponse("Chat members must not be null"));
            }
            Chat chat = new Chat(request.getName());
            ArrayList<Users> users = new ArrayList<>();
            request.getMembers().forEach(user -> users.add(service.getUserById(user)));
            chat.setUsers(users);
            chatRepository.save(chat);
            return ResponseEntity.ok().body(new MessageResponse("Chat was created"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    public ResponseEntity<?> updateChatName(UpdateChatNameRequest request){
        try {
            chatRepository.updateChatByName(request.getId(), request.getName());
            return ResponseEntity.ok().body(new MessageResponse("Chat name was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Chat not found or something went wrong"));
        }
    }

    public ResponseEntity<?> deleteChat(IdRequest request){
        try {
            chatRepository.deleteById(request.getId());
            return ResponseEntity.ok().body(new MessageResponse("Chat was deleted"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Chat not found or something went wrong"));
        }
    }

    public ResponseEntity<?> getChats(IdRequest request){
        try {
            return ResponseEntity.ok().body(chatRepository.getChatsByUserId(request.getId()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("No chats found"));
        }
    }
}
