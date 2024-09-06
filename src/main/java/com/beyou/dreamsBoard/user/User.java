package com.beyou.dreamsBoard.user;

import com.beyou.dreamsBoard.domain.dreamboard.DreamBoard;
import com.beyou.dreamsBoard.dto.RegisterDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;
    @Size(min = 2, message = "User name required a minimum of 2 characters")
    @NotBlank(message = "User is required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password need a minimum of 6 characters")
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created_at;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updated_at;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role", columnDefinition = "TEXT")
    private UserRole role;
    @Column(nullable = false)
    private String img_link;
    @Column(nullable = false)
    private String perfil_phrase;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DreamBoard> dreamBoards;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @PrePersist
    protected void onCreate() {
        LocalDate now = LocalDate.now();
        setCreated_at(Date.valueOf(now));
        setUpdated_at(Date.valueOf(now));
        setRole(UserRole.USER);
        setImg_link("https://i.pinimg.com/564x/e0/87/7c/e0877c5fbec4d096a4334a09bbe25ef5.jpg");
        setPerfil_phrase("Be your best version");
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdated_at(Date.valueOf(LocalDate.now()));
    }


    public User(String name, String email, String password){
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public User(){

    }

    public User(RegisterDTO registerDTO){
        setName(registerDTO.name());
        setEmail(registerDTO.email());
        setPassword(registerDTO.password());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getPerfil_phrase() {
        return perfil_phrase;
    }

    public void setPerfil_phrase(String perfil_phrase) {
        this.perfil_phrase = perfil_phrase;
    }

    public List<DreamBoard> getDreamBoards() {
        return dreamBoards;
    }

    public void setDreamBoards(List<DreamBoard> dreamBoards) {
        this.dreamBoards = dreamBoards;
    }
}
