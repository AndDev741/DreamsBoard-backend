package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.dto.LoginDTO;
import com.beyou.dreamsBoard.security.TokenService;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserResponseDTO;
import com.beyou.dreamsBoard.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private TokenService tokenService;
    @Autowired
    public LoginController(UserService userService, TokenService tokenService){
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> makeLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        try{
            UserResponseDTO user= userService.makeLogin(loginDTO, response);
            return ResponseEntity.ok(user);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
