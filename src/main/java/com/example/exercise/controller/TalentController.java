package com.example.exercise.controller;

import com.example.exercise.dto.talent.AllTalentResponseDto;
import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentResponseDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.talent.TalentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/talents")
public class TalentController {

  private final TalentService talentService;

  // 게시물 작성
  @PostMapping("")
  public ResponseEntity<String> createTalents(TalentRequestDto talentRequestDto, User user){
    String talents = talentService.createTalent(talentRequestDto, user);
    return ResponseEntity.status(HttpStatus.CREATED).body(talents);
  }

  // 게시물 선택조회
  @GetMapping("/{talentsId}")
  public ResponseEntity<TalentResponseDto> getTalent(Long id){
    TalentResponseDto talents = talentService.getTalent(id);
    return ResponseEntity.ok(talents);
  }

  //게시물 전체조회
  @GetMapping("")
  public ResponseEntity<List<AllTalentResponseDto>> getAllTalent(){
    return ResponseEntity.status(HttpStatus.OK).body(talentService.getAllTalent());
  }

  //게시물 수정
  @PatchMapping("/{talentsId}")
  public ResponseEntity<String> updateTalent(Long id,TalentUpdateRequestDto requestDto,User user){
    talentService.updateTalent(id, requestDto, user);
    return ResponseEntity.ok("success");
  }

  //게시물 삭제
  @DeleteMapping("/{talentsId}")
  public ResponseEntity<String> deleteTalent(Long id, User user){
    talentService.deleteTalent(id, user);
    return ResponseEntity.ok("success");
  }
}
