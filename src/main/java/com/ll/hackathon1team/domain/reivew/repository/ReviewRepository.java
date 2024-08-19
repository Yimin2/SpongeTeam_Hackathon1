package com.ll.hackathon1team.domain.reivew.repository;

import com.ll.hackathon1team.domain.reivew.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}