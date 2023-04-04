package com.example.exercise.entity;

import com.example.exercise.dto.gathering.GatheringCommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class GatheringComment extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private Gathering gathering;

    @Column(nullable = false)
    private String content;

    public GatheringComment(String content) {
        this.content = content;

    }

    public void update(GatheringCommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
