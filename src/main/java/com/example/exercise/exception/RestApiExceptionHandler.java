package com.example.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

  // 잘못된 인수 전달 에러 , 유효성 검사 로직에 사용
  @ExceptionHandler(value = {IllegalArgumentException.class})
  public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException e) {
    RestApiException restApiException = new RestApiException(e.getMessage(),
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(
        restApiException,
        HttpStatus.BAD_REQUEST
    );
  }

  // 유효하지 않은 값이 들어왔을 때 발생하는 예외
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException e) {
    RestApiException restApiException = new RestApiException(
        e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
        HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(
        restApiException,
        HttpStatus.BAD_REQUEST
    );
  }

  // Null값 에러
  @ExceptionHandler(value = {NullPointerException.class})
  public ResponseEntity<Object> handleApiRequestException(NullPointerException e) {
    String message = "An internal error occurred";
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    RestApiException restApiException = new RestApiException(message, status.value());
    return new ResponseEntity<>(restApiException, status);
  }
}