package com.example.exercise.entity.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {

  Running("런닝"),
  FootBall("축구"),
  Hiking("등산");

  private final String name;

  CategoryEnum(String name) {
    this.name = name;
  }

  public String getName(){
    return name;
  }
}
