package com.example.exercise.repository;

import com.example.exercise.entity.TalentRecomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentRecommentRepository extends JpaRepository<TalentRecomment, Long> {

}
