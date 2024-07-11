package com.beyou.dreamsBoard.user;

public enum UserRole {
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
