package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.configs.jwt.JwtUtils;
import com.mhl.mycompanybackend.model.*;
import com.mhl.mycompanybackend.pojo.JwtResponse;
import com.mhl.mycompanybackend.pojo.SignInRequest;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.SignupRequest;
import com.mhl.mycompanybackend.repository.PermissionsRepository;
import com.mhl.mycompanybackend.repository.RolesRepository;
import com.mhl.mycompanybackend.repository.UserDataRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import com.mhl.mycompanybackend.userdetails.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    final
    AuthenticationManager authenticationManager;
    final
    UserRepository userRepository;
    final
    UserDataRepository userDataRepository;
    final
    PermissionsRepository permissionsRepository;
    final
    RolesRepository rolesRepository;
    final
    PasswordEncoder passwordEncoder;
    final
    JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, UserDataRepository userDataRepository, PermissionsRepository permissionsRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDataRepository = userDataRepository;
        this.permissionsRepository = permissionsRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<JwtResponse> authenticate(SignInRequest signInRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<>();
        userDetails.getRoles().forEach(oneRole -> roles.add(oneRole.getRole_name()));
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getUserData()));
    }

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        String username = signupRequest.getEmail().split("@")[0];
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username or email exists"));
        }

        UserData userData = new UserData(
                signupRequest.getFirstname(),
                signupRequest.getLastname(),
                signupRequest.getMiddlename()
        );
        userDataRepository.save(userData);
        userDataRepository.findById(userData.getId());
        Users user = new Users(username,
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                userData);

        List<Roles> roles = new ArrayList<>();
        Roles userRole = rolesRepository
                .findRolesByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return authenticate(new SignInRequest(username, signupRequest.getPassword()));
    }
}
