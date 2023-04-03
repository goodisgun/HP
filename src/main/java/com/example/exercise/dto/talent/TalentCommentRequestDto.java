package com.example.exercise.dto.talent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TalentCommentRequestDto {

  private String content;

  public TalentCommentRequestDto build(){
    return new TalentCommentRequestDto(content);
  }

}
