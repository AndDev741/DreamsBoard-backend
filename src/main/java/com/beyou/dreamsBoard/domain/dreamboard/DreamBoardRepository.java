package com.beyou.dreamsBoard.domain.dreamboard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DreamBoardRepository extends JpaRepository<DreamBoard, Long> {
    List<DreamBoard> findAllByUserId(UUID userId);
}
