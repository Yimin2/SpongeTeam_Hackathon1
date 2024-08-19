package com.ll.hackathon1team.domain.reivew.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateDTO  {
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
