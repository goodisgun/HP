package com.example.exercise.dto.talent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TalentReCommentRequestDto {

  private String content;

  public TalentReCommentRequestDto build() {
    return new TalentReCommentRequestDto(content);
  }
}
