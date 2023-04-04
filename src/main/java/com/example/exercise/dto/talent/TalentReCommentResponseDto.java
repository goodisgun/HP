package com.example.exercise.dto.talent;

import com.example.exercise.entity.TalentReComment;
import lombok.Getter;

@Getter
public class TalentReCommentResponseDto {

  private Long id;
  private String nickname;
  private String content;

  public TalentReCommentResponseDto(TalentReComment talentReComment) {
    this.id = talentReComment.getId();
    this.nickname = talentReComment.getUser().getNickname();
    this.content = talentReComment.getContent();
  }
}
