
package com.example.exercise.controller;


import com.example.exercise.dto.gathering.AllGatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringRequestDto;
import com.example.exercise.dto.gathering.GatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;

import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.gathering.GatheringServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringServiceImpl gatheringService;

    //모임 게시글 작성
    @PostMapping
    public ResponseEntity<GatheringResponseDto> createGathering(@RequestBody GatheringRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(gatheringService.createGathering(requestDto,userDetails.getUser()));
    }

    //모임 게시글 수정
    @PatchMapping("/{gatheringId}")
    public ResponseEntity<GatheringResponseDto> updateGathering(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody GatheringUpdateRequestDto updateRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.updateGathering(gatheringId, userDetails.getUser() ,updateRequestDto));
    }

    //모임 게시글 삭제
    @DeleteMapping("/{gatheringId}")
    public void deleteGathering(@PathVariable Long gatheringId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        gatheringService.deleteGathering(gatheringId,userDetails.getUser());
    }

    //모임 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<AllGatheringResponseDto>> getAllGathering(){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.getAllGathering());
    }
    //모임 게시글 상세 조회
    @GetMapping("/{gatheringId}")
    public ResponseEntity<GatheringResponseDto> getGathering(@PathVariable Long gatheringId){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.getGathering(gatheringId));
    }

}
