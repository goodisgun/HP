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

  @Column(nullable = false)
  private int maxEnrollmentCount; // 참가 가능한 최대 인원 수(모집인원)

  @Column(nullable = false)
  private int currentEnrollmentCount; // 현재 참가 인원 수

  @Column(nullable = false)
  private String gatheringTime; // 모임시간

  @OneToMany(mappedBy = "gathering", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<GatheringComment> comments = new ArrayList<>();

  public Gathering(String title, String content, String image, int maxEnrollmentCount,String gatheringTime) {
    this.title = title;
    this.content = content;
    this.image = image;
    this.maxEnrollmentCount =maxEnrollmentCount;
    this.gatheringTime = gatheringTime;
  }

  public void update(GatheringUpdateRequestDto updateRequestDto, int maxEnrollmentCount) {
    this.title = updateRequestDto.getTitle();
    this.content = updateRequestDto.getContent();
    this.image = updateRequestDto.getImage();
    this.maxEnrollmentCount = maxEnrollmentCount;
  }

  //현재 인원
  public void setCurrentEnrollmentCount(int currentEnrollmentCount) {
    this.currentEnrollmentCount = currentEnrollmentCount;
  }

  //최대인원
  public void setMaxEnrollmentCount(int maxEnrollmentCount) {
    this.maxEnrollmentCount = maxEnrollmentCount;
  }
}
