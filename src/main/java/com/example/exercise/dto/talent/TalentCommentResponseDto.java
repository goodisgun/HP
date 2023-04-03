package com.example.exercise.dto.talent;

import com.example.exercise.entity.TalentComment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TalentCommentResponseDto {
  private Long id;
  private String nickname;
  private String content;
  private List<TalentReCommentResponseDto> reComments;

  public TalentCommentResponseDto(TalentComment talentComment) {
    this.id = talentComment.getId();
    this.nickname = talentComment.getUser().getNickname();
    this.content = talentComment.getContent();
    this.reComments = talentComment.getTalentReComments().stream().map(TalentReCommentResponseDto::new).collect(
        Collectors.toList());
  }
}
