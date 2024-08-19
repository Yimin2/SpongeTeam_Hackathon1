package com.ll.hackathon1team.domain.reivew.entity;

import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long courseId;

    private String reviewStatus;
    private String courseLocation;
    private String userMajor;
    private String userFinish;
    private String courseInstructor;
    private boolean courseTeam;
    private int reviewRate;
    private String reviewPro;
    private String reviewCon;
    private String reviewProposal;
    private boolean reviewRecommend;
    private String reviewCertImg;
}
