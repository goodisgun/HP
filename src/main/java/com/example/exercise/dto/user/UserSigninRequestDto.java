package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSigninRequestDto {

  private String username;
  private String password;

  public UserSigninRequestDto build(){
    return new UserSigninRequestDto(username,password);
  }
}
