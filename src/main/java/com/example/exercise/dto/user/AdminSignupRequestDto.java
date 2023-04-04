package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdminSignupRequestDto {

  private String username;
  private String password;
  private String nickname;
  private String adminKey;


  public AdminSignupRequestDto build(){
    return new AdminSignupRequestDto(username, password, nickname, adminKey);
  }
}
