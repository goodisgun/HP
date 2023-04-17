package com.example.exercise.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.talent.TalentCommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TalentCommentControllerTest {

  @Mock
  private TalentCommentServiceImpl talentCommentService;

  @InjectMocks
  private TalentCommentController talentCommentController;

  @Test
  @DisplayName("댓글 생성 성공")
  void createTalentComment() {
    // given
    Long talentId = 1L;
    User user = User.builder()
        .id(1L)
        .username("user")
        .password("1234")
        .nickname("gun")
        .userRole(UserRoleEnum.USER)
        .build();
    TalentCommentRequestDto requestDto = new TalentCommentRequestDto("내용");

    when(talentCommentService.createTalentComment(eq(talentId), eq(user), eq(requestDto)))
        .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

    // when
    ResponseEntity<Void> responseEntity = talentCommentController.createTalentComment(talentId, new UserDetailsImpl(user), requestDto);

    // then
    verify(talentCommentService, times(1)).createTalentComment(eq(talentId), eq(user), eq(requestDto));
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }


  @Test
  @DisplayName("댓글 수정")
  void updateTalentComment() {
    // given
    Long commentId = 1L;
    User user = User.builder()
        .id(1L)
        .username("user")
        .password("1234")
        .nickname("gun")
        .userRole(UserRoleEnum.USER)
        .build();
    TalentCommentRequestDto requestDto = new TalentCommentRequestDto("수정내용");
    when(talentCommentService.updateTalentComment(eq(commentId), any(User.class), eq(requestDto)))
        .thenReturn(ResponseEntity.ok().build());

    // when
    ResponseEntity<Void> responseEntity = talentCommentController.updateTalentComment(commentId, new UserDetailsImpl(user), requestDto);

    // then
    verify(talentCommentService, times(1)).updateTalentComment(eq(commentId), eq(user), eq(requestDto));
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
  }

  @Test
  @DisplayName("댓글 삭제")
  void deleteTalentComment() {
    // given
    Long talentCommentId = 1L;
    User user = User.builder()
        .id(1L)
        .username("user1")
        .password("1234")
        .nickname("gun")
        .userRole(UserRoleEnum.USER)
        .build();

    // when
    talentCommentController.deleteTalentComment(talentCommentId, new UserDetailsImpl(user));

    // then
    verify(talentCommentService, times(1)).deleteComment(eq(talentCommentId), eq(user));
  }
}