package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.User;
import org.springframework.http.ResponseEntity;

public interface TalentCommentService {

  // createTalentComment(댓글생성)
    ResponseEntity<Void> createTalentComment(Long talentId, User user, TalentCommentRequestDto talentCommentRequestDto);

  // updateTalentComment(댓글수정)
  ResponseEntity<Void> updateTalentComment(Long talentCommentId, User user, TalentCommentRequestDto talentCommentRequestDto);

  // deleteTalentComment(댓글삭제)
  void deleteComment(Long talentCommentId, User user);
}
