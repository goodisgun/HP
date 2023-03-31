package com.example.exercise.entity;

import com.example.exercise.entity.enums.UserRoleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum userRole;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Talent> talents = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Gathering> gatherings = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List <TalentComment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<TalentRecomment> reComments = new ArrayList<>();

  public User(Long id, String username, String password, String nickname, String email,
      UserRoleEnum userRole) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.email = email;
    this.userRole = userRole;
  }
}
