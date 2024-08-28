package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.dto.RegisterDTO;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){

        User user = new User(registerDTO);
        String response = userService.registerNewUser(user);
        if(response.equals("success")){
            return ResponseEntity.ok(Map.of("success", "User register successfully!"));
        }else{
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }



    }


}
