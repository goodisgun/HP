package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminSigninRequestDto {

  private String username;
  private String password;
  private String adminKey;

  public AdminSigninRequestDto build(){
    return new AdminSigninRequestDto(username, password, adminKey);
  }
}
