package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Users;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.UserIdAndRoleIdRequest;
import com.mhl.mycompanybackend.pojo.UserRequest;
import com.mhl.mycompanybackend.pojo.UsernameAndPasswordRequest;
import com.mhl.mycompanybackend.repository.UserDataRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, UserDataRepository userDataRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.encoder = encoder;
    }

    public Users getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public ResponseEntity<MessageResponse> setUserData(UserRequest request){
        try {

            userDataRepository.updateUser(
                    request.getFirstname(),
                    request.getLastname(),
                    request.getMiddlename(),
                    request.getPhone_number(),
                    request.getId()
            );
            userRepository.updateUserEmail(request.getId(), request.getEmail());
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

    public ResponseEntity<?> deleteUser(Long id) {
        try {
            userDataRepository.deleteById(id);
            userRepository.deleteFromUserRoles(id);
            userRepository.deleteById(id);
            userRepository.deleteFromUserTasks(id);
            userRepository.deleteFromChatUser(id);
            userRepository.deleteFromUserRoles(id);
            return ResponseEntity.ok().body(new MessageResponse("User was successfully deleted"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    public ResponseEntity<?> updateUserRole(UserIdAndRoleIdRequest request) {
        try {
            userRepository.updateUserRole(request.getUser_id(), request.getRole_id());
            return ResponseEntity.ok().body(new MessageResponse("User role was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
