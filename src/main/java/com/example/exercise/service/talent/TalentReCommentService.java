package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.TalentReCommentRequestDto;
import com.example.exercise.entity.User;
import org.springframework.http.ResponseEntity;

public interface TalentReCommentService {

  // reComment(대댓글 작성)
  ResponseEntity<Void> createTalentReComment(Long talentCommentId, User user, TalentReCommentRequestDto talentReCommentRequestDto);

  // updateReComment(대댓글 수정)
   ResponseEntity<Void> updateTalentReComment(Long talentReCommentId, User user, TalentReCommentRequestDto talentReCommentRequestDto);

  // deleteReComment(대댓글 삭제)
   void deleteTalentReComment(Long talentReCommentId, User user);

}
