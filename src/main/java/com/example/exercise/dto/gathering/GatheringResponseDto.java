package com.example.exercise.dto.gathering;

import com.example.exercise.entity.Gathering;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GatheringResponseDto {

    private Long Id;
    private String nickname;
    private String title;
    private String image;
    private String content;
    private int maxEnrollmentCount; //모집인원
    private String gatheringTime;
    private List<GatheringCommentResponseDto> comments;


    public GatheringResponseDto(Gathering gathering, int maxEnrollmentCount) {
        this.nickname = gathering.getUser().getNickname();
        this.title = gathering.getTitle();
        this.image = gathering.getImage();
        this.content = gathering.getContent();
        this.comments = gathering.getComments().stream()
                .map(GatheringCommentResponseDto::new)
                .collect(Collectors.toList());
        this.maxEnrollmentCount = maxEnrollmentCount;
        this.gatheringTime = gathering.getGatheringTime();
    }
}
