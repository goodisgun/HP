package com.example.exercise.dto.talent;

import com.example.exercise.entity.TalentComment;
import lombok.Getter;

@Getter
public class TalentCommentResponseDto {
  private Long id;
  private String nickname;
  private String content;

  public TalentCommentResponseDto(TalentComment talentComment) {
    this.id = talentComment.getId();
    this.nickname = talentComment.getUser().getNickname();
    this.content = talentComment.getContent();
  }
}
