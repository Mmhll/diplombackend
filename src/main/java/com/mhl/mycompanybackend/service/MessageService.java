package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Message;
import com.mhl.mycompanybackend.model.Users;
import com.mhl.mycompanybackend.pojo.MessageChatRequest;
import com.mhl.mycompanybackend.pojo.MessageRequest;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.repository.ChatRepository;
import com.mhl.mycompanybackend.repository.MessageRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public ResponseEntity<MessageResponse> addMessage(MessageRequest request) {
        Users user = userRepository.findById(request.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            Message message = new Message(request.getMessage(), user);
            messageRepository.save(message);
            messageRepository.addMessageToChat(request.getChat_id(), message.getId());
            chatRepository.updateChatDate(Timestamp.from(Instant.now()), request.getChat_id());
            return ResponseEntity.ok().body(new MessageResponse("Message was saved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> deleteMessage(Long messageId, Long chatId) {
        try {
            Message message = messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("Message not found"));
            messageRepository.delete(message);
            messageRepository.deleteMessageChat(chatId, messageId);
            return ResponseEntity.ok().body(new MessageResponse("Message was deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
        }
    }
}
