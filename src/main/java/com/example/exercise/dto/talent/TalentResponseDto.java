package com.example.exercise.dto.talent;

import com.example.exercise.entity.Talent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TalentResponseDto {

  private Long id;
  private String nickname;
  private String title;
  private String content;
  private String image;
//  private List<TalentCommentResponseDto> comments;


  public TalentResponseDto(Talent talent) {
    this.id = talent.getId();
    this.nickname = talent.getUser().getNickname();
    this.title = talent.getTitle();
    this.content = talent.getContent();
    this.image = talent.getImage();
  }
}
