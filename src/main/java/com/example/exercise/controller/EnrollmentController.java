package com.example.exercise.controller;

import com.example.exercise.dto.gathering.EnrollmentResponseDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.gathering.EnrollmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentServiceImpl enrollmentService;

    //참여 신청 & 취소
    @PostMapping("/gathering/{gatheringId}")
    private void update(@PathVariable Long gatheringId, User user){
        enrollmentService.enrollment(gatheringId,user);
    }

    //내가 참여한 모임 보기
    @GetMapping("/my-gathering")
    public ResponseEntity <List<EnrollmentResponseDto>> getMyGathering(Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getMyGathering(userId));
    }

    //모임 게시글에서 참여 신청한 사람 리스트 보기
    @GetMapping("/gathering/{gatheringId}/participants")
    public ResponseEntity <List<EnrollmentResponseDto>> getParticipantList(@PathVariable Long gatheringId){
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getParticipantList(gatheringId));
    }



}

