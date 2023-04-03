package com.example.exercise.repository;

import com.example.exercise.entity.TalentReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentReCommentRepository extends JpaRepository<TalentReComment, Long> {

}
