package com.example.exercise.dto.talent;

import com.example.exercise.entity.Talent;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TalentResponseDto {

  private Long id;
  private String nickname;
  private String title;
  private String content;
  private String image;
  private List<TalentCommentResponseDto> comments;


  public TalentResponseDto(Talent talent) {
    this.id = talent.getId();
    this.nickname = talent.getUser().getNickname();
    this.title = talent.getTitle();
    this.content = talent.getContent();
    this.image = talent.getImage();
    this.comments = talent.getComments().stream().map(TalentCommentResponseDto::new).collect(
        Collectors.toList());
  }
}
