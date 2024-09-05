package com.beyou.dreamsBoard.security.passwordreset;

import com.beyou.dreamsBoard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUser(User user);
}
