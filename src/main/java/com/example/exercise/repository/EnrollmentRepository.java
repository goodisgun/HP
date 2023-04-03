package com.example.exercise.repository;

import com.example.exercise.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository  extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByGatheringIdAndUserId(Long gatheringId, Long userId);

    List<Enrollment> findAllByGatheringId(Long gatheringId);

    List<Enrollment> findAllByUserId(Long userId);
}
