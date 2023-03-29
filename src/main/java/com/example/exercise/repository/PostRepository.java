package com.example.exercise.repository;

import com.example.exercise.entity.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Talent, Long> {

}
