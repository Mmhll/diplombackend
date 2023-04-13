package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.models.Users;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.UserRequest;
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest;
import com.mhl.mycompanybackend.repository.UserDataRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private PasswordEncoder encoder;
    public Users getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public ResponseEntity<MessageResponse> setUserData(UserRequest request){
        try {

            userDataRepository.updateUser(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getMiddlename(),
                    request.getId()
            );
            userRepository.updateUserEmailAndUsername(request.getId(), request.getEmail());
            return ResponseEntity.ok().body(new MessageResponse("User was updated"));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }
    public ResponseEntity<MessageResponse> setUserPassword(UsernameAndPasswordRequest request){
        try {
            userRepository.updateUserPassword(request.getId(), encoder.encode(request.getPassword()));
            return ResponseEntity.ok().body(new MessageResponse("Password was changed"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }
    public ResponseEntity<List<Users>> findAllUsers(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    public ResponseEntity<?> deleteUser(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            userRepository.deleteFromUserRoles(user.getId());
            userRepository.delete(user);
            return ResponseEntity.ok().body(new MessageResponse("User was successfully deleted"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
