package com.example.exercise.service.talent;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.Talent;
import com.example.exercise.entity.TalentComment;
import com.example.exercise.entity.User;
import com.example.exercise.repository.TalentRepository;
import com.example.exercise.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TalentCommentServiceImplTest {

  @InjectMocks
  TalentCommentServiceImpl talentCommentService;

  @Mock
  UserRepository userRepository;

  @Mock
  TalentRepository talentRepository;

  @Test
  @DisplayName("댓글 생성")
  void createTalentComment() {

    // given
    Talent talent = mock(Talent.class);
    User user = mock(User.class);

    TalentCommentRequestDto requestDto = TalentCommentRequestDto.builder().content("content").build();

    TalentComment comment = new TalentComment(user, talent, requestDto);

    given(talentRepository.findById(talent.getId())).willReturn(Optional.of(talent));
    given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

    // when
    ResponseEntity<Void> response = talentCommentService.createTalentComment(talent.getId(), user,
        requestDto);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  @DisplayName("댓글 수정")
  void updateTalentComment() {
  }

  @Test
  void deleteComment() {

  }
}