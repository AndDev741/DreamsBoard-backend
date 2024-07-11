package com.beyou.dreamsBoard.user;

import com.beyou.dreamsBoard.dto.RegisterDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String password;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date created_at;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updated_at;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role", columnDefinition = "TEXT")
    UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        setCreated_at(Date.valueOf(now));
        setUpdated_at(Date.valueOf(now));
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdated_at(Date.valueOf(LocalDate.now()));
    }


    public User(String name, String email, String password){
        LocalDate now = LocalDate.now();
        setName(name);
        setEmail(email);
        setPassword(password);
        setCreated_at(Date.valueOf(now));
        setUpdated_at(Date.valueOf(now));
        setRole(UserRole.USER);
    }

    public User(){
        setRole(UserRole.USER);
    }

    public User(RegisterDTO registerDTO){
        setName(registerDTO.name());
        setEmail(registerDTO.email());
        setPassword(registerDTO.password());
        setRole(UserRole.USER);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
