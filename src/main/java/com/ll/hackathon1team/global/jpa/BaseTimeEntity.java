package com.ll.hackathon1team.global.jpa;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Schema(description = "생성 시간")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Schema(description = "수정 시간")
    private LocalDateTime modifiedAt;
}
