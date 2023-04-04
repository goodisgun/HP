package com.example.exercise.dto.gathering;

import com.example.exercise.entity.Enrollment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnrollmentResponseDto {

    private Long id; //참여신청 아이디(pk)
    private Long gatheringId;
    private String title; // 참여신청한 게시글의 제목인데 있어야 하나 싶지만 일단 넣어봅니다.
    private String nickname;
    private String phoneNumber;

    public EnrollmentResponseDto(Enrollment enrollment) {
        this.gatheringId = enrollment.getGathering().getId();
        this.title = enrollment.getGathering().getTitle();
        this.nickname = enrollment.getNickname();
        this.phoneNumber = enrollment.getPhoneNumber();
    }
}
