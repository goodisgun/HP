package com.example.exercise.controller;

import com.example.exercise.dto.talent.TalentReCommentRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.talent.TalentReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talents/{talentId}/comments/{commentId}/reComments")
public class TalentReCommentController {

  private final TalentReCommentService talentReCommentService;
  @PostMapping
  public ResponseEntity<Void> createTalentReComment(Long talentCommentId, User user, TalentReCommentRequestDto talentReCommentRequestDto){
    talentReCommentService.createTalentReComment(talentCommentId, user, talentReCommentRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{reCometId}")
  public ResponseEntity<Void> updateTalentReComment(Long talentReCommentId, User user, TalentReCommentRequestDto talentReCommentRequestDto){
    talentReCommentService.updateTalentReComment(talentReCommentId, user, talentReCommentRequestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{reCometId}")
  public void deleteTalentReComment(Long talentReCommentId, User user){
    talentReCommentService.deleteTalentReComment(talentReCommentId, user);
  }
}
