package com.example.exercise.dto.gathering;

import com.example.exercise.entity.Gathering;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatheringResponseDto {

    private Long gatheringId;
    private String nickname;
    private String title;
    private String image;
    private String content;
    private String gatheringTime;
    private int memberNum;

    public GatheringResponseDto(Gathering gathering) {
        this.nickname = gathering.getUser().getNickname();
        this.title = gathering.getTitle();
        this.image = gathering.getImage();
        this.content = gathering.getContent();
//        this.gatheringTime = gathering.getGatheringTime;
//        this.memberNum = memberNum;
    }
}
