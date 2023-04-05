package com.mhl.mycompanybackend.utils;

import com.mhl.mycompanybackend.pojo.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {
    public ResponseEntity<MessageResponse> badRequest(){
        return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));
    }
}
