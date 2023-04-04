package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.GatheringCommentRequestDto;
import com.example.exercise.dto.gathering.GatheringCommentResponseDto;
import com.example.exercise.entity.User;

public interface GatheringCommentService {

    //댓글 작성
    GatheringCommentResponseDto createGatheringComment(Long gatheringId,User user, GatheringCommentRequestDto commentRequestDto);

    //댓글 수정
    GatheringCommentResponseDto updateGatheringComment(Long gatheringCommentId, User user, GatheringCommentRequestDto commentRequestDto);

    //댓글 삭제
    void deleteGatheringComment(Long gatheringCommentId, User user);

}
