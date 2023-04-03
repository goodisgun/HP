package com.example.exercise.dto.gathering;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatheringCommentRequestDto {
    private String content;
}
