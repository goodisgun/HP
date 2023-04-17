package com.example.exercise.dto.talent;

import com.example.exercise.entity.Talent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TalentUpdateResponseDto {

  private String title;
  private String content;
  private String image;

  public TalentUpdateResponseDto(Talent talent) {
    this.title = talent.getTitle();
    this.content = talent.getContent();
    this.image = talent.getImage();
  }
}
