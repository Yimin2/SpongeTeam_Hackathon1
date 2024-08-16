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

        if (reviewUpdateDTO.getCourseId() != null) review.setCourseId(reviewUpdateDTO.getCourseId());
        if (reviewUpdateDTO.getReviewStatus() != null) review.setReviewStatus(reviewUpdateDTO.getReviewStatus());
        if (reviewUpdateDTO.getCourseLocation() != null) review.setCourseLocation(reviewUpdateDTO.getCourseLocation());
        if (reviewUpdateDTO.getUserMajor() != null) review.setUserMajor(reviewUpdateDTO.getUserMajor());
        if (reviewUpdateDTO.getUserFinish() != null) review.setUserFinish(reviewUpdateDTO.getUserFinish());
        if (reviewUpdateDTO.getCourseInstructor() != null) review.setCourseInstructor(reviewUpdateDTO.getCourseInstructor());
        review.setCourseTeam(reviewUpdateDTO.isCourseTeam());
        if (reviewUpdateDTO.getReviewRate() > 0) review.setReviewRate(reviewUpdateDTO.getReviewRate());
        if (reviewUpdateDTO.getReviewPro() != null) review.setReviewPro(reviewUpdateDTO.getReviewPro());
        if (reviewUpdateDTO.getReviewCon() != null) review.setReviewCon(reviewUpdateDTO.getReviewCon());
        if (reviewUpdateDTO.getReviewProposal() != null) review.setReviewProposal(reviewUpdateDTO.getReviewProposal());
        review.setReviewRecommend(reviewUpdateDTO.isReviewRecommend());
        if (reviewUpdateDTO.getReviewCertImg() != null) review.setReviewCertImg(reviewUpdateDTO.getReviewCertImg());


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