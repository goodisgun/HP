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


  @Builder
  public Enrollment(User user, Gathering gathering, String nickname) {
    this.user = user;
    this.gathering = gathering;
    this.nickname = nickname;
  }

}
