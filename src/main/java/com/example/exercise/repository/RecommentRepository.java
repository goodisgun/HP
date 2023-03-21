package com.example.exercise.repository;

import com.example.exercise.entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment, Long> {

}
