package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;


@Getter
public class UserUsernameDto {

  private String username;

  public UserUsernameDto(String username) {
    this.username = username;
  }
}
