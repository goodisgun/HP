package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUsernameDto {

  private String username;

  public UserUsernameDto build(){
    return new UserUsernameDto(username);
  }
}
