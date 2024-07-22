package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.mainElement.MainElement;
import com.beyou.dreamsBoard.domain.reason.Reason;

import java.util.List;

public record CreateBoardDTO(Long userId, String background_img, String title, String secondary_img,
                             String secondary_phrase, List<Reason> reasons, List<MainElement> mainElements) {

}
