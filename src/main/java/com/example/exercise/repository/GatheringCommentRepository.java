package com.example.exercise.repository;

import com.example.exercise.entity.GatheringComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringCommentRepository extends JpaRepository<GatheringComment, Long> {
}
