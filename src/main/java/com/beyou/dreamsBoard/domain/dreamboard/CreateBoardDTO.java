package com.beyou.dreamsBoard.domain.dreamboard;

public record CreateBoardDTO(java.util.UUID userId, String background_img, String title, String mainObjective_text,
                             String mainObjective_img, String objective_text, String objective_img,
                             String reason_title) {

}
