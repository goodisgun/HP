package com.example.exercise.entity;

import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Gathering extends TimeStamped {

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

  @OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<GatheringComment> comments = new ArrayList<>();

  public Gathering(String title, String content, String image) {
    this.title = title;
    this.content = content;
    this.image = image;
//    this.gatheringTime;
//    this.memberNum;
  }

  public void update(GatheringUpdateRequestDto updateRequestDto) {
    this.title = updateRequestDto.getTitle();
    this.content = updateRequestDto.getContent();
    this.image = updateRequestDto.getImage();
//    this.gatheringTime = updateRequestDto.getGatheringTime;
    }
}
