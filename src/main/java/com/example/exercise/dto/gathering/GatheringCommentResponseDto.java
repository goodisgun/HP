package com.example.exercise.dto.gathering;

import com.example.exercise.entity.GatheringComment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatheringCommentResponseDto {

    private Long Id;
    private String nickname;
    private String content;

    public GatheringCommentResponseDto(GatheringComment gatheringComment) {
        this.nickname = gatheringComment.getUser().getNickname();
        this.content = gatheringComment.getContent();
    }
}
