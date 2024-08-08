package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.reason.Reason;
import com.beyou.dreamsBoard.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dreamboard")
public class DreamBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "background_img")
    private String background_img;
    @Column(name = "title")
    private String title;
    @Column(name = "mainObjective_text")
    private String mainObjectiveText;
    @Column(name = "mainObjective_img")
    private String mainObjectiveImg;
    @Column(name = "objective_text")
    private String objective_text;
    @Column(name = "objective_img")
    private String objective_img;
    @Column(name = "reason_title")
    private String reason_title;
    @OneToMany(mappedBy = "dreamBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reason> reasons;

    public DreamBoard(){

    }

    public DreamBoard(Long id, User user, String background_img, String title, String mainObjective_img,
                      String mainObjective_text, String objective_text, String objective_img, String reason_title,
                      List<Reason> reasons) {
        this.id = id;
        this.user = user;
        this.background_img = background_img;
        this.title = title;
        this.mainObjectiveImg = mainObjective_img;
        this.mainObjectiveText = mainObjective_text;
        this.objective_text = objective_text;
        this.objective_img = objective_img;
        this.reason_title = reason_title;
        this.reasons = reasons;
    }

    public DreamBoard(CreateBoardDTO createBoardDTO, User user, List<Reason> reasons){
        setUser(user);
        setBackground_img(createBoardDTO.background_img().getOriginalFilename());
        setTitle(createBoardDTO.title());
        setMainObjective_text(createBoardDTO.mainObjective_text());
        setMainObjectiveImg(createBoardDTO.mainObjective_img().getOriginalFilename());
        setObjective_text(createBoardDTO.objective_text());
        setObjective_img(createBoardDTO.objective_img().getOriginalFilename());
        setReason_title(createBoardDTO.reason_title());
        setReasons(reasons);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

}
