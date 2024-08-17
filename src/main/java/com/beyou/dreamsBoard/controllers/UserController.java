package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserEditDTO;
import com.beyou.dreamsBoard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserEditDTO userEditDTO){
        try{
            Optional<User> userEditOptional = repository.findById(userEditDTO.id());
            if(userEditOptional.isPresent()){
                User userEdit = userEditOptional.get();
                userEdit.setImg_link(userEditDTO.img_link());
                userEdit.setName(userEditDTO.name());
                userEdit.setPerfil_phrase(userEditDTO.perfil_phrase());
                try{
                    repository.save(userEdit);
                    return ResponseEntity.ok().body(Map.of("success", "User edited successfully"));
                }catch(Exception e){
                    return ResponseEntity.badRequest().body(Map.of("error", "Error trying to edit the user"));
                }
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying find user"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Unknown error"));
    }
}
