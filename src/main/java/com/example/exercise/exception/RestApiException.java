package com.example.exercise.exception;

import lombok.Getter;

@Getter
public class RestApiException {

  private String errorMessage;
  private int errorCode;

  public RestApiException(String errorMessage, int errorCode) {
    this.errorMessage = errorMessage;
    this.errorCode = errorCode;
  }

}
