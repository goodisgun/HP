package com.example.exercise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Enrollment{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @JoinColumn
  @ManyToOne
  private User user;

  @ManyToOne
  @JoinColumn
  private Post post;

  @Column(nullable = false)
  private String userName;

  @Column(nullable = false)
  private String email;

  public Enrollment(Long id, User user, Post post, String userName, String email) {
    Id = id;
    this.user = user;
    this.post = post;
    this.userName = userName;
    this.email = email;
  }


}
