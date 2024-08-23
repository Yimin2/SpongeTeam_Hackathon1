package com.ll.hackathon1team.domain.user.entity;

import com.ll.hackathon1team.global.jpa.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "users")
@Getter
@Setter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "유저 ID")
    @Column(name = "user_id")
    private Long id;

    @Column(name = "userName")
    @Schema(description = "Google 유저 이름")
    private String userName;

    @Schema(description = "Google 이메일 주소")
    @Column(name = "email", nullable = false)
    private String email;

    @Schema(description = "Google 계정 프로필 이미지 파일")
    @Column(name = "image")
    private String image;

    @Schema(description = "유저 권한")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private Role role = Role.ROLE_USER;
}