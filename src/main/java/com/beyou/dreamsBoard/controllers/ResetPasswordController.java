package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.dto.ResetPasswordDTO;
import com.beyou.dreamsBoard.security.passwordreset.EmailService;
import com.beyou.dreamsBoard.security.passwordreset.PasswordResetToken;
import com.beyou.dreamsBoard.security.passwordreset.PasswordResetTokenRepository;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/resetPassword")
public class ResetPasswordController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    public ResetPasswordController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload){
        String email = payload.get("email");
        try{
            User user = userRepository.findByEmail(email).orElseThrow();

            String token = UUID.randomUUID().toString();

            createPasswordResetTokenForUser(user, token);

            String resetUrl = getResetPasswordUrl(token);

            emailService.sendSimpleMessage(email, "Reset Your Password",
                    "To reset your password, click the link below:\n" + resetUrl);

            return ResponseEntity.ok().body(Map.of("success", "An email to recover your password was send!"));

        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find the user"));
        }
    }

    @PutMapping
    public ResponseEntity<?> editPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        String token = resetPasswordDTO.token();
        String newPassword = resetPasswordDTO.newPassword();

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if(passwordResetToken == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "this token is not valid, generate another one"));
        } else if (passwordResetToken.getExpiryDate().before((new Date()))) {
            return  ResponseEntity.badRequest().body(Map.of("error", "This token is expired, generate another one"));
        } else {
            User user = passwordResetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            passwordResetTokenRepository.delete(passwordResetToken);

            return ResponseEntity.ok().body(Map.of("success", "Password redefined successfully!"));
        }
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUser(user);

        if (existingToken != null){
            existingToken.setUser(user);
            existingToken.setToken(token);
            existingToken.setExpiryDate(calculateExpiryDate());
            passwordResetTokenRepository.save(existingToken);
        } else {
            PasswordResetToken newToken = new PasswordResetToken(token, user, calculateExpiryDate());
            passwordResetTokenRepository.save(newToken);
        }

    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    private String getResetPasswordUrl(String token){
        String appUrl = "http://localhost:3000/recoverPassword";
        return appUrl + "?token=" + token;
    }
}
