package com.example.exercise.controller;

import com.example.exercise.dto.gathering.GatheringCommentRequestDto;
import com.example.exercise.dto.gathering.GatheringCommentResponseDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.gathering.GatheringCommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering/{gatheringId}/comments")
public class GatheringCommentController {

    private final GatheringCommentServiceImpl gatheringCommentService;
    //댓글 작성
    @PostMapping
    public ResponseEntity<GatheringCommentResponseDto> createGatheringComment(@PathVariable Long gatheringId, User user, @RequestBody GatheringCommentRequestDto commentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gatheringCommentService.createGatheringComment(gatheringId,user, commentRequestDto));
    }

    //댓글 수정
    @PatchMapping("/{gatheringCommentId}")
    public ResponseEntity<GatheringCommentResponseDto> updateGatheringComment(@PathVariable Long gatheringCommentId, User user,@RequestBody GatheringCommentRequestDto commentRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(gatheringCommentService.updateGatheringComment(gatheringCommentId,user,commentRequestDto));
    }

    //댓글 삭제
    @DeleteMapping("/{gatheringCommentId}")
    public void deleteGatheringComment(@PathVariable Long gatheringCommentId, User user){
        gatheringCommentService.deleteGatheringComment(gatheringCommentId,user);
    }

}

