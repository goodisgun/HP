package com.example.exercise.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserNicknameDto {

  private String nickname;

  public UserNicknameDto build(){
    return new UserNicknameDto(nickname);
  }

}
