package com.example.exercise.dto.gathering;


import com.example.exercise.entity.Gathering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AllGatheringResponseDto {
    private String nickname;
    private String title;
    private String gatheringTime;
    private int  maxEnrollmentCount;

    public AllGatheringResponseDto(Gathering gathering) {
        this.nickname = gathering.getUser().getNickname();
        this.title = gathering.getTitle();
        this.maxEnrollmentCount = getMaxEnrollmentCount();
    }
}
