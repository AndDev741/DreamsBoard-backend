package com.beyou.dreamsBoard.domain.dreamboard;

import org.springframework.web.multipart.MultipartFile;

public record CreateBoardDTO(Long userId, String background_img, String title, String mainObjective_text,
                             String mainObjective_img, String objective_text, String objective_img,
                             String reason_title) {

}
