package com.ll.hackathon1team.domain.reivew.repository;

import com.ll.hackathon1team.domain.reivew.entity.Review;
import com.ll.hackathon1team.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE (:courseName IS NULL OR LOWER(r.courseName) LIKE LOWER(CONCAT('%', :courseName, '%'))) " +
            "AND (:schoolName IS NULL OR LOWER(r.schoolName) LIKE LOWER(CONCAT('%', :schoolName, '%'))) " +
            "AND (:courseInstructor IS NULL OR LOWER(r.courseInstructor) LIKE LOWER(CONCAT('%', :courseInstructor, '%')))")
    Page<Review> searchByKeyword(@Param("courseName") String courseName,
                                 @Param("schoolName") String schoolName,
                                 @Param("courseInstructor") String courseInstructor, Pageable pageable);
    void deleteByUser(User user);
    Page<Review> findAllByUserEmail(String email, Pageable pageable);
}