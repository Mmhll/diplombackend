package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Chat;
import com.mhl.mycompanybackend.model.Users;
import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.repository.ChatRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService service;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserService service, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.service = service;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addChat(CreateChatRequest request) {
        try {
            if (request.getName().isEmpty() || request.getName() == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Chat name must not be null"));
            }
            if (request.getMembers().isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Chat members must not be null"));
            }
            Chat chat = new Chat(request.getName());
            ArrayList<Users> users = new ArrayList<>();
            request.getMembers().forEach(user -> users.add(service.getUserById(user)));
            chat.setUsers(users);
            chatRepository.save(chat);
            return ResponseEntity.ok().body(new MessageResponse("Chat was created"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    public ResponseEntity<MessageResponse> updateChatName(UpdateChatNameRequest request) {
        try {
            chatRepository.updateChatByName(request.getId(), request.getName());
            return ResponseEntity.ok().body(new MessageResponse("Chat name was updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Chat not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> deleteChat(Long id) {
        try {
            chatRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("Chat was deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Chat not found or something went wrong"));
        }
    }

    public ResponseEntity<?> getChats(String id) {
        /*try {*/

        return ResponseEntity.ok().body(chatRepository.findChatsByUsersOrderByUpdatedAtDescCreatedAtDesc(userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new RuntimeException("user not found"))));
        /*} catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("No chats found"));
        }*/
    }

    public ResponseEntity<MessageResponse> deleteChatMember(Long userId, Long chatId) {
        try {
            chatRepository.deleteUserFromChat(chatId, userId);
            return ResponseEntity.ok().body(new MessageResponse("User was deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found in this chat or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> addChatMember(UserIdChatIdRequest request) {
        try {
            chatRepository.addUserToChat(request.getChat_id(), request.getUser_id());
            return ResponseEntity.ok().body(new MessageResponse("User was added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found or something went wrong"));
        }
    }

    public ResponseEntity<?> getChat(String id){
        try {
            return ResponseEntity.ok().body(chatRepository.findById(Long.parseLong(id)).get());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong or chat doesn't exists"));
        }
    }
}
