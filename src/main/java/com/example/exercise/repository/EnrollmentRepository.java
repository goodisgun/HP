package com.example.exercise.repository;

import com.example.exercise.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository  extends JpaRepository<Enrollment, Long> {

}
