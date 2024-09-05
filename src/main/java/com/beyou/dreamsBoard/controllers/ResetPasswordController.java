package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.security.passwordreset.EmailService;
import com.beyou.dreamsBoard.security.passwordreset.PasswordResetToken;
import com.beyou.dreamsBoard.security.passwordreset.PasswordResetTokenRepository;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/resetPassword")
public class ResetPasswordController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestBody Map<String, String> payload){
        String email = payload.get("email");
        try{
            User user = userRepository.findByEmail(email).orElseThrow();

            String token = UUID.randomUUID().toString();

            createPasswordResetTokenForUser(user, token);

            String resetUrl = getResetPasswordUrl(request, token);

            emailService.sendSimpleMessage(email, "Reset Your Password",
                    "To reset your password, click the link below:\n" + resetUrl);

            return ResponseEntity.ok().body(Map.of("success", "An email to recover your password was send!"));

        }catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find the user"));
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

    private String getResetPasswordUrl(HttpServletRequest request, String token){
        String appUrl = request.getRequestURL().toString();
        return appUrl + "?token=" + token;
    }
}
