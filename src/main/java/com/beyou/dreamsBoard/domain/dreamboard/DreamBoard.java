package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.reason.Reason;
import com.beyou.dreamsBoard.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dreamboard")
public class DreamBoard {
    @Id
    @UuidGenerator()
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "background_img")
    private String background_img;
    @Column(name = "background_img_id")
    private String background_img_id;
    @Column(name = "title")
    private String title;
    @Column(name = "mainObjective_text")
    private String mainObjectiveText;
    @Column(name = "mainObjective_img")
    private String mainObjectiveImg;
    @Column(name = "mainObjective_img_id")
    private String mainObjectiveImg_id;
    @Column(name = "objective_text")
    private String objective_text;
    @Column(name = "objective_img")
    private String objective_img;
    @Column(name = "objective_img_id")
    private String objective_img_id;
    @Column(name = "reason_title")
    private String reason_title;
    @OneToMany(mappedBy = "dreamBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reason> reasons;

    public DreamBoard(){

    }

    public DreamBoard(UUID id, User user, String background_img, String background_img_id, String title, String mainObjective_img,
                      String mainObjectiveImg_id, String mainObjective_text, String objective_text, String objective_img, String objective_img_id,
                      String reason_title, List<Reason> reasons) {
        this.id = id;
        this.user = user;
        this.background_img = background_img;
        this.background_img_id = background_img_id;
        this.title = title;
        this.mainObjectiveImg = mainObjective_img;
        this.mainObjectiveImg_id = mainObjectiveImg_id;
        this.mainObjectiveText = mainObjective_text;
        this.objective_text = objective_text;
        this.objective_img = objective_img;
        this.objective_img_id = objective_img_id;
        this.reason_title = reason_title;
        this.reasons = reasons;
    }

    public DreamBoard(CreateBoardDTO createBoardDTO, User user, List<Reason> reasons){
        setUser(user);
        setBackground_img(createBoardDTO.background_img());
        setBackground_img_id(createBoardDTO.background_img_id());
        setTitle(createBoardDTO.title());
        setMainObjective_text(createBoardDTO.mainObjective_text());
        setMainObjectiveImg(createBoardDTO.mainObjective_img());
        setMainObjectiveImg_id(createBoardDTO.mainObjective_img_id());
        setObjective_text(createBoardDTO.objective_text());
        setObjective_img(createBoardDTO.objective_img());
        setObjective_img_id(createBoardDTO.objective_img_id());
        setReason_title(createBoardDTO.reason_title());
        setReasons(reasons);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBackground_img() {
        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainObjectiveImg() {
        return mainObjectiveImg;
    }

    public void setMainObjective_text(String mainObjective_text) {
        this.mainObjectiveText = mainObjective_text;
    }

    public String getMainObjective_text() {
        return mainObjectiveText;
    }

    public void setMainObjectiveImg(String mainObjectiveImg) {
        this.mainObjectiveImg = mainObjectiveImg;
    }

    public String getObjective_text() {
        return objective_text;
    }

    public void setObjective_text(String objective_text) {
        this.objective_text = objective_text;
    }

    public String getObjective_img() {
        return objective_img;
    }

    public void setObjective_img(String objective_img) {
        this.objective_img = objective_img;
    }

    public String getReason_title() {
        return reason_title;
    }

    public void setReason_title(String reason_title) {
        this.reason_title = reason_title;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public String getBackground_img_id() {
        return background_img_id;
    }

    public void setBackground_img_id(String background_img_id) {
        this.background_img_id = background_img_id;
    }

    public String getMainObjectiveText() {
        return mainObjectiveText;
    }

    public void setMainObjectiveText(String mainObjectiveText) {
        this.mainObjectiveText = mainObjectiveText;
    }

    public String getMainObjectiveImg_id() {
        return mainObjectiveImg_id;
    }

    public void setMainObjectiveImg_id(String mainObjectiveImg_id) {
        this.mainObjectiveImg_id = mainObjectiveImg_id;
    }

    public String getObjective_img_id() {
        return objective_img_id;
    }

    public void setObjective_img_id(String objective_img_id) {
        this.objective_img_id = objective_img_id;
    }
}
