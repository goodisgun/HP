package com.example.exercise.repository;


import com.example.exercise.entity.TalentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentCommentRepository  extends JpaRepository<TalentComment, Long> {

}
