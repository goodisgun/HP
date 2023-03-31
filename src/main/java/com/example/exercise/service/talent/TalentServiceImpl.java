package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.AllTalentResponseDto;
import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentResponseDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import com.example.exercise.dto.talent.TalentUpdateResponseDto;
import com.example.exercise.entity.Talent;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.repository.TalentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

  private final TalentRepository talentRepository;

  // 게시글 생성
  @Override
  public String createTalent(TalentRequestDto talentRequestDto, User user) {
    Talent talent = new Talent(talentRequestDto,user);
    talentRepository.save(talent);
    return "success";
  }

  //게시글 상세조회
  @Override
  @Transactional(readOnly = true)
  public TalentResponseDto getTalent(Long talentId) {
    Talent talent = _findTalent(talentId);
    return new TalentResponseDto(talent);
  }

  // 게시글 전체조회
  @Override
  @Transactional(readOnly = true)
  public List<AllTalentResponseDto> getAllTalent() {
    List<Talent> talents = talentRepository.findAll();

    List<AllTalentResponseDto> allTalentResponseDtoList = new ArrayList<>();
    if (talents.isEmpty()){
      throw new IllegalStateException("게시글이 존재하지 않습니다.");
    } else {
      for (Talent talent : talents){
        AllTalentResponseDto allTalentResponseDto = new AllTalentResponseDto(talent);
        allTalentResponseDtoList.add(allTalentResponseDto);
      }
    }
    return allTalentResponseDtoList;
  }

  // 게시글 수정
  @Override
  public TalentUpdateResponseDto updatePost(Long talentId, TalentUpdateRequestDto requestDto,
      User user) {
    Talent talent = _findTalent(talentId);

    if (user.getUserRole() != UserRoleEnum.ADMIN){
      if(!talent.getUser().getId().equals(user.getId())){
        throw new IllegalArgumentException("글 작성자만 수정이 가능합니다.");
      }
    }
    talent.updateTalent(requestDto);
    return new TalentUpdateResponseDto(talent);
  }

  //게시글 삭제
  @Override
  public String deleteTalent(Long talentId, User user) {
    Talent talent = _findTalent(talentId);

    if (user.getUserRole() != UserRoleEnum.ADMIN) {
      if (!talent.getUser().getId().equals(user.getId())) {
        throw new IllegalArgumentException("글 작성자만 삭제 가능합니다.");
      }
    }
    talentRepository.deleteById(talentId);
    return "success";
  }

  //중복코드
  private Talent _findTalent(Long talentId){
    Talent talent= talentRepository.findById(talentId).orElseThrow(
        ()->new IllegalArgumentException("게시글이 존재하지 않습니다.")
    );
    return talent;
  }
}
