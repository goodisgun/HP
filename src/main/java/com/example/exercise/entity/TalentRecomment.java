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
public class TalentRecomment extends TimeStamped{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @JoinColumn
  @ManyToOne
  private TalentComment talentComment;

  @JoinColumn
  @ManyToOne
  private User user;

  @Column(nullable = false)
  private String content;

  public TalentRecomment(Long id, TalentComment comment, User user, String content) {
    Id = id;
    this.talentComment = comment;
    this.user = user;
    this.content = content;
  }


}
