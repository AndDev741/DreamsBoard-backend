package com.beyou.dreamsBoard.domain.dreamboard;

import org.springframework.web.multipart.MultipartFile;

public record CreateBoardDTO(Long userId, MultipartFile background_img, String title, String mainObjective_text,
                             MultipartFile mainObjective_img, String objective_text, MultipartFile objective_img,
                             String reason_title) {

}
