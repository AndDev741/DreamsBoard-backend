package com.beyou.dreamsBoard.user;

import com.beyou.dreamsBoard.dto.LoginDTO;
import com.beyou.dreamsBoard.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();

    }

    public String makeLogin(LoginDTO login){
        Optional<User> userLogin = repository.findByEmail(login.email());
        if(userLogin.isPresent()){
            var token = tokenService.generateToken(userLogin.get());
            User user = userLogin.get();
            if(passwordEncoder.matches(login.password(), user.getPassword())){
                return token;
            }
        }
        return "";
    }

}
