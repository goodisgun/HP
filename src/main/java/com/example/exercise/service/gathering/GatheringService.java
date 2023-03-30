package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.AllGatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringRequestDto;
import com.example.exercise.dto.gathering.GatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;
import com.example.exercise.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GatheringService {

    // 게시글 작성
    GatheringResponseDto createGathering(GatheringRequestDto requestDto);
    // 게시글 전체 조회
    List<AllGatheringResponseDto> getAllGathering();

    // 게시글 상세 조회
    GatheringResponseDto getGathering( @PathVariable Long gatheringId);

    // 게시글 수정
    GatheringResponseDto updateGathering(@PathVariable Long gatheringId, User user, GatheringUpdateRequestDto updateRequestDto);

    // 게시글 삭제
    void deleteGathering(Long gatheringId, User user);

}
