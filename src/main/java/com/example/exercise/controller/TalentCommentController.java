package com.example.exercise.controller;

import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.talent.TalentCommentService;
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
@RequestMapping("/talents/{talentId}/comments")
public class TalentCommentController {

  private final TalentCommentService talentCommentService;

@PostMapping
  public ResponseEntity<Void> createTalentComment(Long talentId, User user, TalentCommentRequestDto requestDto){
  talentCommentService.createTalentComment(talentId, user, requestDto);
  return ResponseEntity.status(HttpStatus.CREATED).build();
}

@PatchMapping("/{commentId}")
  public ResponseEntity<Void> updateTalentComment(Long talentCommentId, User user,
    TalentCommentRequestDto talentCommentRequestDto){
  talentCommentService.updateTalentComment(talentCommentId, user, talentCommentRequestDto);
  return ResponseEntity.ok().build();
}

@DeleteMapping("/{commentId}")
  public void deleteTalentComment(Long talentCommentId, User user){
  talentCommentService.deleteComment(talentCommentId, user);
}
}
