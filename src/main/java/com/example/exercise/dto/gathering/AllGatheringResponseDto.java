package com.example.exercise.dto.gathering;


import com.example.exercise.entity.Gathering;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllGatheringResponseDto {
    private String nickname;
    private String title;
    private String gatheringTime;
    private int memberNum;

    public AllGatheringResponseDto(Gathering gathering) {
        this.nickname = gathering.getUser().getNickname();
        this.title = gathering.getTitle();
    }
}
