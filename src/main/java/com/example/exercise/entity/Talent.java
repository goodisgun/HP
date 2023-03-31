package com.example.exercise.entity;

import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Talent extends TimeStamped{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn
  @ManyToOne
  private User user;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  private String image;

  @OneToMany(mappedBy = "talent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TalentComment> comments = new ArrayList<>();


  @Builder
  public Talent (TalentRequestDto requestDto, User user){
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.image = requestDto.getImage();
    this.user = user;
  }

  public void updateTalent(TalentUpdateRequestDto requestDto){
    if (requestDto.getTitle() != null && !requestDto.getTitle().isEmpty()) {
      this.title = requestDto.getTitle();
    }
    if (requestDto.getContent() != null && !requestDto.getContent().isEmpty()) {
      this.content = requestDto.getContent();
    }
    if (requestDto.getImage() != null && !requestDto.getImage().isEmpty()) {
      this.image = requestDto.getImage();
    }
  }



}
