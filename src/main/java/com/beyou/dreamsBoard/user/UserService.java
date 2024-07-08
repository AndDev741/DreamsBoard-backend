package com.beyou.dreamsBoard.user;

import com.beyou.dreamsBoard.dto.LoginDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();

    }

    public Optional<User> makeLogin(LoginDTO login){
        Optional<User> userLogin = repository.findByEmail(login.email());
        if(userLogin.isPresent()){
            User user = userLogin.get();
            if(passwordEncoder.matches(login.password(), user.getPassword())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
