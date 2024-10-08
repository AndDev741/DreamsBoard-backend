package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.user.dto.EditEmailDTO;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.dto.EditPasswordDTO;
import com.beyou.dreamsBoard.user.dto.UserEditDTO;
import com.beyou.dreamsBoard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/verify/{id}")
    public ResponseEntity<Map> verifyUser(@PathVariable UUID id){
        return ResponseEntity.ok().body(Map.of("success", "User logged"));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        try{
            Optional<User> userToDeleteOptional = repository.findById(id);
            if(userToDeleteOptional.isPresent()){
                User userToDelete = userToDeleteOptional.get();
                try{
                    repository.delete(userToDelete);
                    return ResponseEntity.ok().body(Map.of("success", "User deleted successfully"));
                }catch(Exception e){
                    return ResponseEntity.badRequest().body(Map.of("error", "Error trying to delete the user"));
                }
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find the user"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Unknown Error"));
    }

    @PostMapping("/editEmail")
    public ResponseEntity<?> editEmail(@RequestBody EditEmailDTO editEmailDTO){
        try{
            User user = repository.findById(editEmailDTO.id()).orElseThrow();
            user.setEmail(editEmailDTO.newEmail());

            try{
                repository.save(user);
                return ResponseEntity.ok().body(Map.of("success", "Email edited successfully"));
            }catch(Exception e){
                return ResponseEntity.badRequest().body(Map.of("error", "Error trying to edit the email"));
            }

        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find user"));
        }
    }

    @PostMapping("/editPassword")
    public ResponseEntity<?> editPassword(@RequestBody EditPasswordDTO editPasswordDTO){
        try{
            String oldPassword = editPasswordDTO.oldPass();
            User user = repository.findById(editPasswordDTO.id()).orElseThrow();
            if(passwordEncoder.matches(oldPassword, user.getPassword())){
                String newPassword = editPasswordDTO.newPass();
                user.setPassword(passwordEncoder.encode(newPassword));
                try{
                    repository.save(user);
                    return ResponseEntity.ok().body(Map.of("success", "Password edited successfully!"));
                }catch(Exception e){
                    return ResponseEntity.badRequest().body(Map.of("error", "Error trying to save password"));
                }
            }else{
                return ResponseEntity.badRequest().body(Map.of("error", "Please, try edit again"));
            }
        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find user"));
        }
    }
}
