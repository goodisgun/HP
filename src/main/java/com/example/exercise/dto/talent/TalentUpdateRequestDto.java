package com.example.exercise.dto.talent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TalentUpdateRequestDto {

  private String title;
  private String content;
  private String image;

  public TalentUpdateRequestDto build(){
    return new TalentUpdateRequestDto(title, content, image);
  }
}
