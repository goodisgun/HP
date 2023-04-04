package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSignupRequestDto {

  private String username;
  private String password;
  private String nickname;

  public UserSignupRequestDto build(){
    return new UserSignupRequestDto(username, password, nickname);
  }
}
