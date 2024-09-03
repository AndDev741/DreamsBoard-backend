package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.reason.Reason;

import java.util.List;

public record CreateBoardDTO(java.util.UUID userId, String title, String mainObjective_text, String objective_text,
                             String reason_title, String background_img, String background_img_id,
                             String mainObjective_img, String mainObjective_img_id, String objective_img,
                             String objective_img_id, List<Reason> reasons) {

}
