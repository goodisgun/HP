package com.example.exercise.dto.talent;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TalentRequestDto {

  private String title;
  private String content;
  private String image;

 public TalentRequestDto build(){
   return new TalentRequestDto(title, content, image);
 }

  public TalentRequestDto(String title, String content, String image) {
    this.title = title;
    this.content = content;
    this.image = image;
  }
}
