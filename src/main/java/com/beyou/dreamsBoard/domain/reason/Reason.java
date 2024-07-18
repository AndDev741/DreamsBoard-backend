package com.beyou.dreamsBoard.domain.reason;

import com.beyou.dreamsBoard.domain.dreamboard.DreamBoard;
import jakarta.persistence.*;

@Entity
@Table(name = "reason")
public class Reason {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dreamboard_id", nullable = false)
    private DreamBoard dreamBoard;
    @Column
    private String img_link;
    @Column
    private String phrase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DreamBoard getDreamBoard() {
        return dreamBoard;
    }

    public void setDreamBoard(DreamBoard dreamBoard) {
        this.dreamBoard = dreamBoard;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
