package com.ll.hackathon1team.domain.reivew.repository;

import com.ll.hackathon1team.domain.reivew.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE (:courseId IS NULL OR r.courseId = :courseId) AND (:courseInstructor IS NULL OR r.courseInstructor = :courseInstructor)")
    Page<Review> searchByKeyword(@Param("courseId") Long courseId, @Param("courseInstructor") String courseInstructor, Pageable pageable);
}