package com.example.exercise.controller;


import com.example.exercise.dto.gathering.AllGatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringRequestDto;
import com.example.exercise.dto.gathering.GatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.gathering.GatheringServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering/posts")
public class GatheringController {

    private final GatheringServiceImpl gatheringService;

    //모임 게시글 작성
    @PostMapping
    public ResponseEntity<GatheringResponseDto> createGathering(@RequestBody GatheringRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gatheringService.createGathering(requestDto));
    }

    //모임 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<GatheringResponseDto> updateGathering(@PathVariable Long gatheringId, User user, @RequestBody GatheringUpdateRequestDto updateRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.updateGathering(gatheringId, user,updateRequestDto));
    }

    //모임 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deleteGathering(@PathVariable Long gatheringId, User user){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.deleteGathering(gatheringId,user));
    }

    //모임 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<AllGatheringResponseDto>> getAllGathering(){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.getAllGathering());
    }
    //모임 게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<GatheringResponseDto> getGathering(@PathVariable Long gatheringId){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringService.getGathering(gatheringId));
    }

}


