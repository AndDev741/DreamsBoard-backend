package com.beyou.dreamsBoard.user;

import java.util.UUID;

public record UserEditDTO (UUID id, String img_link, String name, String perfil_phrase) {
}
