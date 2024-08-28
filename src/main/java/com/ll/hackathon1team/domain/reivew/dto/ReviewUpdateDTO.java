package com.ll.hackathon1team.domain.reivew.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewUpdateDTO  {
    private String courseName;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private String schoolName;
    private String schoolAddress;
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
