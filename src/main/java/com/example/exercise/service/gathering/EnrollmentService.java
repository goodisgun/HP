package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.EnrollmentResponseDto;
import com.example.exercise.entity.User;

import java.util.List;

public interface EnrollmentService {

    //참여신청 & 취소
    void enrollment(Long gatheringId, User user);

    //내가 참여한 모임 보기
    List<EnrollmentResponseDto> getMyGathering(Long userId);

    //모임 게시글에서 참여 신청한 사람 리스트 보기
    List<EnrollmentResponseDto> getParticipantList(Long gatheringId);

}
