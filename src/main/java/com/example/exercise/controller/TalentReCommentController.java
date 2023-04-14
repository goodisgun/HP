package com.example.exercise.controller;

import com.example.exercise.dto.talent.TalentReCommentRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.talent.TalentReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talents/{talentId}/comments/{commentId}/reComments")
public class TalentReCommentController {

  private final TalentReCommentService talentReCommentService;
  @PostMapping
  public ResponseEntity<Void> createTalentReComment(@PathVariable Long talentCommentId, @AuthenticationPrincipal UserDetailsImpl
      userDetails, @RequestBody TalentReCommentRequestDto talentReCommentRequestDto){
    talentReCommentService.createTalentReComment(talentCommentId, userDetails.getUser(), talentReCommentRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{reCometId}")
  public ResponseEntity<Void> updateTalentReComment(@PathVariable Long talentReCommentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TalentReCommentRequestDto talentReCommentRequestDto){
    talentReCommentService.updateTalentReComment(talentReCommentId, userDetails.getUser(), talentReCommentRequestDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{reCometId}")
  public void deleteTalentReComment(@PathVariable Long talentReCommentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
    talentReCommentService.deleteTalentReComment(talentReCommentId, userDetails.getUser());
  }
}
