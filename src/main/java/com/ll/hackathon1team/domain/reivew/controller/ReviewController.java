package com.ll.hackathon1team.domain.reivew.controller;

import com.ll.hackathon1team.domain.reivew.dto.ReviewUpdateDTO;
import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.reivew.service.ReviewService;
import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Review createdReview = reviewService.createReview(review, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody ReviewUpdateDTO reviewUpdateDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Review updatedReview = reviewService.updateReview(id, reviewUpdateDTO, user);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build();
    }
}
