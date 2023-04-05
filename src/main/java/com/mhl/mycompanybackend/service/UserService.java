package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.Users;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.UserAndPasswordRequest;
import com.mhl.mycompanybackend.pojo.UserRequest;
import com.mhl.mycompanybackend.repository.UserDataRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<MessageResponse> setUserData(UserRequest request){
        try {
            Users user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("not found"));

            userDataRepository.updateUser(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getMiddlename(),
                    user.getId()
            );
            userRepository.updateUserEmailAndUsername(user.getId(), request.getEmail());
            return ResponseEntity.ok().body(new MessageResponse("User was updated"));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    public ResponseEntity<MessageResponse> setUserPassword(UserAndPasswordRequest request){
        try {
            Users user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
            userRepository.updateUserPassword(user.getId(), encoder.encode(request.getPassword()));
            return ResponseEntity.ok().body(new MessageResponse("Password was changed"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
