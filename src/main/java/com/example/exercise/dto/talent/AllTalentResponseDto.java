package com.example.exercise.dto.talent;

import com.example.exercise.entity.Talent;
import lombok.Getter;

@Getter
public class AllTalentResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String image;

  public AllTalentResponseDto(Talent talent) {
    this.id = talent.getId();
    this.nickname = talent.getUser().getNickname();
    this.title = talent.getTitle();
    this.image = talent.getImage();
  }
}
