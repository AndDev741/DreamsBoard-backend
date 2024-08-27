package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.reason.Reason;

import java.util.List;
import java.util.UUID;

public record EditBoardDTO (UUID dreamBoardId, String title, String mainObjective_text, String objective_text,
                            String reason_title, String background_img, String mainObjective_img,
                            String objective_img , List<Reason> reasons) {
}
