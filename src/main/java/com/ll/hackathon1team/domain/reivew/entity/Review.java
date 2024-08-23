package com.ll.hackathon1team.domain.reivew.entity;

import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.global.jpa.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "개별 후기 ID")
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "작성자 유저 ID")
    private User user;

    @Schema(description = "교육 ID")
    private Long courseId;

    @Schema(description = "심사 상태")
    private String reviewStatus;

    @Schema(description = "수업 방식")
    private String courseLocation;

    @Schema(description = "본인전공여부")
    private String userMajor;

    @Schema(description = "수강 상태")
    private String userFinish;

    @Schema(description = "강사명")
    private String courseInstructor;

    @Schema(description = "팀플 포함 여부")
    private boolean courseTeam;

    @Schema(description = "별점")
    private int reviewRate;

    @Schema(description = "좋았던 점")
    private String reviewPro;

    @Schema(description = "아쉬웠던 점")
    private String reviewCon;

    @Schema(description = "학원에 바라는 점")
    private String reviewProposal;

    @Schema(description = "교육추천여부")
    private boolean reviewRecommend;

    @Schema(description = "수강 인증 이미지 파일")
    private String reviewCertImg;
}
