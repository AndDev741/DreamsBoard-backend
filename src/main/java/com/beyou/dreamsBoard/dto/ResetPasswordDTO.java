package com.beyou.dreamsBoard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(@NotBlank String token, @NotBlank
@Size(min = 6, message = "Password need a minimum of 6 characters")String newPassword) {
}
