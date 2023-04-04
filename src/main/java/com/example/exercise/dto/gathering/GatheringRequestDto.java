package com.example.exercise.dto.gathering;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatheringRequestDto {

    private String title;
    private String content;
    private String image;
    private String gatheringTime;
    private int maxEnrollmentCount;


}
