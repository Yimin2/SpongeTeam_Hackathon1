package com.ll.hackathon1team.domain.reivew.controller;

import com.ll.hackathon1team.domain.reivew.dto.ReviewUpdateDTO;
import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.reivew.service.ReviewService;
import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<Review>> getAllReviews(
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "courseInstructor", required = false) String courseInstructor,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("reviewID"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));

        Page<Review> reviewPage = reviewService.searchReviews(courseId, courseInstructor, pageable);
        return ResponseEntity.ok(reviewPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestParam("reviewData") String reviewData,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();
            Review review = reviewService.createReview(reviewData, file, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable("id") Long id,
            @RequestParam("reviewData") String reviewData,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
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
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build();
    }
}
