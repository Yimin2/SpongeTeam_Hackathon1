package com.ll.hackathon1team.domain.reivew.controller;

import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.reivew.service.ReviewService;
import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review API", description = "Review CRUD API")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    @Operation(summary = "모든 리뷰 조회", description = "검색, 필터링, 페이징 (http://localhost:8080/api/reviews?courseName=교육이름&page=0&size=10)")
    public ResponseEntity<Page<Review>> getAllReviews(
            @RequestParam(value = "courseName", required = false) String courseName,
            @RequestParam(value = "schoolName", required = false) String schoolName,
            @RequestParam(value = "courseInstructor", required = false) String courseInstructor,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("reviewID"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<Review> reviewPage = reviewService.searchReviews(courseName, schoolName, courseInstructor, pageable);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "리뷰 ID로 조회", description = "리뷰 ID를 통해 특정 리뷰 조회")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "리뷰 생성", description = "새로운 리뷰 생성")
    public ResponseEntity<Review> createReview(
            @RequestPart("reviewData") @Parameter(description = "리뷰 데이터", required = true) String reviewData,
            @RequestPart(value = "file", required = false) @Parameter(description = "첨부 파일") MultipartFile file,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(value = "Authorization", required = true) @Parameter(description = "Bearer 인증 토큰", required = true) String authorization) {
        try {
            User user = userDetails.getUser();
            Review review = reviewService.createReview(reviewData, file, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "리뷰 수정", description = "리뷰 ID로 리뷰를 수정")
    public ResponseEntity<Review> updateReview(
            @PathVariable("id") Long id,
            @RequestPart("reviewData") String reviewData,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(value = "Authorization", required = true) @Parameter(description = "Bearer 인증 토큰", required = true) String authorization) {
        try {
            User user = userDetails.getUser();
            Review updatedReview = reviewService.updateReview(id, reviewData, file, user);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제")
    public ResponseEntity<Void> deleteReview(
            @PathVariable("id") Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(value = "Authorization", required = true) @Parameter(description = "Bearer 인증 토큰", required = true) String authorization) {
        User user = userDetails.getUser();
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "리뷰 심사 상태 수정", description = "관리자가 리뷰 ID로 리뷰의 상태를 수정 api/reviews/{id}/status?reviewStatus=심사중")
    public ResponseEntity<Review> updateReviewStatus(
            @PathVariable("id") Long id,
            @RequestParam("reviewStatus") String reviewStatus,
            @RequestHeader(value = "Authorization", required = true) @Parameter(description = "Bearer 인증 토큰", required = true) String authorization) {
        try {
            Review updatedReview = reviewService.updateReviewStatus(id, reviewStatus);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}