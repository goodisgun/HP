package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.GatheringCommentRequestDto;
import com.example.exercise.dto.gathering.GatheringCommentResponseDto;
import com.example.exercise.entity.GatheringComment;
import com.example.exercise.entity.User;
import com.example.exercise.repository.GatheringCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringCommentServiceImpl implements GatheringCommentService {

    private final GatheringCommentRepository gatheringCommentRepository;

    //댓글 작성
    @Override
    public GatheringCommentResponseDto createGatheringComment(Long gatheringId,User user, GatheringCommentRequestDto commentRequestDto) {
      GatheringComment gatheringComment = new GatheringComment(commentRequestDto.getContent());
      gatheringCommentRepository.save(gatheringComment);

      return new GatheringCommentResponseDto(gatheringComment);
    }

    //댓글 수정
    @Override
    public GatheringCommentResponseDto updateGatheringComment(Long gatheringCommentId, User user, GatheringCommentRequestDto commentRequestDto) {
        GatheringComment gatheringComment = findGatheringComment(gatheringCommentId);
        validateUser(gatheringComment, user);
        gatheringComment.update(commentRequestDto);
        return new GatheringCommentResponseDto(gatheringComment);
    }

    //댓글 삭제
    @Override
    public String deleteGatheringComment(Long gatheringCommentId, User user) {
        GatheringComment gatheringComment = findGatheringComment(gatheringCommentId);
        validateUser(gatheringComment, user);
        gatheringCommentRepository.delete(gatheringComment);
        return "SUCCESS";
    }

    //중복 코드_존재여부
    private GatheringComment findGatheringComment(Long gatheringCommentId){
        return gatheringCommentRepository.findById(gatheringCommentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 모임이 존재하지 않습니다.")
        );
    }

    //중복 코드_권한여부
    private void validateUser(GatheringComment gatheringComment, User user) {
        if(!gatheringComment.getUser().equals(user)){
            throw new IllegalArgumentException("수정/삭제할 권한이 없습니다.");
        }
    }
}

