package com.ll.hackathon1team.domain.reivew.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hackathon1team.domain.reivew.dto.ReviewUpdateDTO;
import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.reivew.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.ll.hackathon1team.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private S3Service s3Service;
    private ObjectMapper objectMapper;

    public Page<Review> searchReviews(Long courseId, String courseInstructor, Pageable pageable) {
        return reviewRepository.searchByKeyword(courseId, courseInstructor, pageable);
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(String reviewData, MultipartFile file, User user) throws IOException {
        String fileUrl = null;

        if (file != null && !file.isEmpty()) {
            fileUrl = s3Service.uploadFile(file);
        }

        Review review = objectMapper.readValue(reviewData, Review.class);
        review.setUser(user);
        review.setReviewCertImg(fileUrl);

        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, String reviewData, MultipartFile file, User user) throws IOException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found with id " + id));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to edit this review");
        }

        ReviewUpdateDTO reviewUpdateDTO = objectMapper.readValue(reviewData, ReviewUpdateDTO.class);

        if (file != null && !file.isEmpty()) {
            String fileUrl = s3Service.uploadFile(file);
            review.setReviewCertImg(fileUrl);
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