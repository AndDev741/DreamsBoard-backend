package com.beyou.dreamsBoard.domain.reason;

import com.beyou.dreamsBoard.domain.dreamboard.DreamBoard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "reason")
public class Reason {
    @Id
    @UuidGenerator
    @Column(updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dreamboard_id", nullable = false)
    @JsonIgnore
    private DreamBoard dreamBoard;
    @Column
    private String title;
    @Column
    private String img;
    @Column
    private String text;

    public Reason(UUID id, DreamBoard dreamBoard, String title, String img, String text) {
        this.id = id;
        this.dreamBoard = dreamBoard;
        this.title = title;
        this.img = img;
        this.text = text;
    }

    public Reason (String title, String img, String text, DreamBoard dreamBoard) {
        this.title = title;
        this.img = img;
        this.text = text;
        this.dreamBoard = dreamBoard;
    }

    public Reason() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DreamBoard getDreamBoard() {
        return dreamBoard;
    }

    public void setDreamBoard(DreamBoard dreamBoard) {
        this.dreamBoard = dreamBoard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
