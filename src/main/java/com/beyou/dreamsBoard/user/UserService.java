package com.beyou.dreamsBoard.user;

import com.beyou.dreamsBoard.dto.LoginDTO;
import com.beyou.dreamsBoard.security.TokenService;
import com.beyou.dreamsBoard.user.dto.UserResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String registerNewUser(User user) {
        String verifyEmail = user.getEmail();
        Optional<User> verifyUser = repository.findByEmail(verifyEmail);
        if(verifyUser.isPresent()){
            return "error";
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return "success";
        }

    }

    public boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();

    }

    public Map makeLogin(LoginDTO login, HttpServletResponse response){
        Optional<User> userLogin = repository.findByEmail(login.email());
        if(userLogin.isPresent()){
            User user = userLogin.get();
            if(passwordEncoder.matches(login.password(), user.getPassword())){
                var token = tokenService.generateToken(userLogin.get());
                addJwtTokenToResponse(response, token);
                return Map.of("success", new UserResponseDTO(user.getId(), user.getName(), user.getImg_link(), user.getPerfil_phrase()));
            }
        }else{
            return Map.of("error", "Email or Password invalid");
        }
        return Map.of("error", "Email or Password invalid");
    }

    private void addJwtTokenToResponse(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);

        response.setHeader(HttpHeaders.SET_COOKIE, "jwt=" + token + "; Max-Age=604800; Path=/; Secure; HttpOnly; SameSite=Lax");
    }

}
