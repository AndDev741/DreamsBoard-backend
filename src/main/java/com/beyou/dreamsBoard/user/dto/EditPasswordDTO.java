package com.beyou.dreamsBoard.user.dto;

import java.util.UUID;

public record EditPasswordDTO (UUID id, String oldPass, String newPass) {
}
