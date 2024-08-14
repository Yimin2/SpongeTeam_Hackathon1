package com.ll.hackathon1team.domain.reivew.service;

import com.ll.hackathon1team.domain.reivew.dto.ReviewUpdateDTO;
import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.reivew.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.ll.hackathon1team.domain.user.entity.User;
import com.ll.hackathon1team.domain.user.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review, User user) {
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO, User user) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found with id " + id));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to edit this review");
        }

        review.setCourseId(reviewUpdateDTO.getCourseId());
        review.setReviewStatus(reviewUpdateDTO.getReviewStatus());
        review.setCourseLocation(reviewUpdateDTO.getCourseLocation());
        review.setUserMajor(reviewUpdateDTO.getUserMajor());
        review.setUserFinish(reviewUpdateDTO.getUserFinish());
        review.setCourseInstructor(reviewUpdateDTO.getCourseInstructor());
        review.setCourseTeam(reviewUpdateDTO.isCourseTeam());
        review.setReviewRate(reviewUpdateDTO.getReviewRate());
        review.setReviewPro(reviewUpdateDTO.getReviewPro());
        review.setReviewCon(reviewUpdateDTO.getReviewCon());
        review.setReviewProposal(reviewUpdateDTO.getReviewProposal());
        review.setReviewRecommend(reviewUpdateDTO.isReviewRecommend());
        review.setReviewCertImg(reviewUpdateDTO.getReviewCertImg());

        return reviewRepository.save(review);
    }

    public void deleteReview(Long id, User user) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found with id " + id));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this review");
        }

        reviewRepository.delete(review);
    }
}