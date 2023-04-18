package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileUpdateDto {

  private String nickname;
  private String introduction;
  private String image;

}
