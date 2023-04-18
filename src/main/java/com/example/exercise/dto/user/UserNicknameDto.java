package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserNicknameDto {

  private String nickname;

  public UserNicknameDto(String nickname) {
    this.nickname = nickname;
  }
}
