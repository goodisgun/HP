package com.example.exercise.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gathering_id")
  private Gathering gathering;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String phoneNumber;

  @Builder
  public Enrollment(User user, Gathering gathering, String nickname, String phoneNumber) {
    this.user = user;
    this.gathering = gathering;
    this.nickname = nickname;
    this.phoneNumber = validatePhoneNumber(phoneNumber);
  }

  // phoneNumber 유효성 검사 로직
  private String validatePhoneNumber(String phoneNumber) {
    String regex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    if (phoneNumber == null || !phoneNumber.matches(regex)) {
      throw new IllegalArgumentException("올바른 핸드폰 번호를 입력해 주세요.");
    }
    return phoneNumber;
  }
}
