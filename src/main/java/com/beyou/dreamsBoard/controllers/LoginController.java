package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.dto.LoginDTO;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> makeLogin(@RequestBody LoginDTO loginDTO){
        Optional<User> login = userService.makeLogin(loginDTO);
        if(login.isPresent()){
            return ResponseEntity.ok("Login successfully");
        }
        return ResponseEntity.status(401).body("Invalid Email or password");

    }

}
