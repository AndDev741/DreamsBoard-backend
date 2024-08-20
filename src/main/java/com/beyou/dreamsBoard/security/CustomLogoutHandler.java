package com.beyou.dreamsBoard.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Certifique-se de que o mesmo atributo Secure seja usado
        cookie.setPath("/"); // Mesmo Path usado na criação do cookie
        cookie.setMaxAge(0); // Set MaxAge to 0 to delete the cookie

        response.addCookie(cookie);
    }
}
