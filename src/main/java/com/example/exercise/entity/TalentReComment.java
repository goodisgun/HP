package com.example.exercise.entity;


import com.example.exercise.dto.talent.TalentReCommentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class TalentReComment extends TimeStamped{

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

  @Builder
  public TalentReComment(TalentComment talentComment, User user, TalentReCommentRequestDto talentReCommentRequestDto){
    this.talentComment = talentComment;
    this.user = user;
    this.content = talentReCommentRequestDto.getContent();
  }

  public void updateTalentReComment(String content){
    this.content = content;
  }

}
