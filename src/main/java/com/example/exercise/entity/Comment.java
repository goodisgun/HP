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
public class Comment extends TimeStamped{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @JoinColumn
  @ManyToOne
  private User user;

  @JoinColumn
  @ManyToOne
  private Post post;


  @Column(nullable = false)
  private String content;

  public Comment(Long id, User user, Post post, String content) {
    Id = id;
    this.user = user;
    this.post = post;
    this.content = content;
  }
}
