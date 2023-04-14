package com.example.exercise.controller;

import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.talent.TalentCommentService;
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
@RequestMapping("/talents/{talentId}/comments")
public class TalentCommentController {

  private final TalentCommentService talentCommentService;

@PostMapping
  public ResponseEntity<Void> createTalentComment(@PathVariable Long talentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TalentCommentRequestDto requestDto){
  talentCommentService.createTalentComment(talentId, userDetails.getUser(), requestDto);
  return ResponseEntity.status(HttpStatus.CREATED).build();
}

@PatchMapping("/{commentId}")
  public ResponseEntity<Void> updateTalentComment(@PathVariable Long talentCommentId, @AuthenticationPrincipal UserDetailsImpl userDetails,
    @RequestBody TalentCommentRequestDto talentCommentRequestDto){
  talentCommentService.updateTalentComment(talentCommentId, userDetails.getUser(), talentCommentRequestDto);
  return ResponseEntity.ok().build();
}

@DeleteMapping("/{commentId}")
  public void deleteTalentComment(@PathVariable Long talentCommentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
  talentCommentService.deleteComment(talentCommentId, userDetails.getUser());
}
}
