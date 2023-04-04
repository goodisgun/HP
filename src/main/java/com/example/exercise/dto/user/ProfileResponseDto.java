package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponseDto {

  private Long id;
  private String nickname;
  private String introduction;
  private String image;
  private String role;

  public ProfileResponseDto build(){
    return new ProfileResponseDto(id,nickname,introduction,image,role);
  }
}
