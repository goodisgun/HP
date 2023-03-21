package com.example.exercise.entity;

import com.example.exercise.entity.enums.CategoryEnum;
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
public class Post extends TimeStamped{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn
  @ManyToOne
  private User user;

  @Column(nullable = false)
  private String title;

  @Column
  private String image;

  private CategoryEnum category;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  public Post(Long id, User user, String title, String image, CategoryEnum category,
      List<Comment> comments) {
    this.id = id;
    this.user = user;
    this.title = title;
    this.image = image;
    this.category = category;
    this.comments = comments;
  }
}
