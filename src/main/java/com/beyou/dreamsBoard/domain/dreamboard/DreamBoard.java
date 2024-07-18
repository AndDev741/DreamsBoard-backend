package com.beyou.dreamsBoard.domain.dreamboard;

import com.beyou.dreamsBoard.domain.mainElement.MainElement;
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

    @Column(nullable = false)
    private String background_img;
    @Column(nullable = false)
    private String title;
    @Column()
    private String secondary_img;
    @Column()
    private String secondary_phrase;
    @OneToMany(mappedBy = "dreamBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reason> reasons;
    @OneToMany(mappedBy = "dreamBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainElement> mainElements;

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

    public String getSecondary_img() {
        return secondary_img;
    }

    public void setSecondary_img(String secondary_img) {
        this.secondary_img = secondary_img;
    }

    public String getSecondary_phrase() {
        return secondary_phrase;
    }

    public void setSecondary_phrase(String secondary_phrase) {
        this.secondary_phrase = secondary_phrase;
    }

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public List<MainElement> getMainElements() {
        return mainElements;
    }

    public void setMainElements(List<MainElement> mainElements) {
        this.mainElements = mainElements;
    }
}
