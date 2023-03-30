package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.AllTalentResponseDto;
import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentResponseDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import com.example.exercise.dto.talent.TalentUpdateResponseDto;
import com.example.exercise.entity.Talent;
import com.example.exercise.entity.User;
import java.util.List;

public interface TalentService {

//  createTalent (게시글 생성)
  String createTalent(TalentRequestDto talentRequestDto, User user);

  //getTalent (게시글 선택조회)
   TalentResponseDto getTalent(Long talentId);

   //getAllTalent (게시글 전체 조회)
  List<AllTalentResponseDto> getAllTalent();

  //updateTalent (게시글 수정)
  TalentUpdateResponseDto updatePost(Long talentId, TalentUpdateRequestDto requestDto, User user);

  //deletePost (게시글 삭제)
  String deleteTalent(Long talentId, User user);



}
