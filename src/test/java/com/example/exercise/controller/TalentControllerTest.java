package com.example.exercise.controller;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.exercise.dto.talent.AllTalentResponseDto;
import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentResponseDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.talent.TalentServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TalentControllerTest {

  @InjectMocks
  private TalentController talentController;

  @Mock
  private TalentServiceImpl talentService;

  @Mock
  private UserDetailsImpl userDetails;

  @Test
  @DisplayName("게시글 작성")
  void createTalents() {
    // given
    TalentRequestDto.TalentRequestDtoBuilder talentBuilder = TalentRequestDto.builder()
        .title("title")
        .content("content")
        .image("image");
    TalentRequestDto talentRequestDto = talentBuilder.build();

    // when
    when(talentService.createTalent(talentRequestDto, userDetails.getUser())).thenReturn("Success");
    ResponseEntity<String> response = talentController.createTalents(talentRequestDto, userDetails);

    // then
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Success", response.getBody());
  }

  @Test
  @DisplayName("게시글 상세조회")
  void getTalent() {
    //given
    Long id = 1L;
    TalentResponseDto talentResponseDto = TalentResponseDto.builder()
        .id(id)
        .title("title")
        .content("content")
        .build();
    when(talentService.getTalent(id)).thenReturn(talentResponseDto);

    //when
    ResponseEntity<TalentResponseDto> response = talentController.getTalent(id);
    TalentResponseDto result = response.getBody();

    //then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(result);
    assertEquals(id, result.getId());
    assertEquals("title", result.getTitle());
    assertEquals("content", result.getContent());
  }

  @Test
  @DisplayName("게시글 전체 조회")
  void getAllTalent() {
    // given
    AllTalentResponseDto responseDto1 = mock(AllTalentResponseDto.class);
    AllTalentResponseDto responseDto2 = mock(AllTalentResponseDto.class);
    List<AllTalentResponseDto> responseDtos = Arrays.asList(responseDto1, responseDto2);
    when(talentService.getAllTalent()).thenReturn(responseDtos);

    // when
    ResponseEntity<List<AllTalentResponseDto>> response = talentController.getAllTalent();

    // then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(responseDtos, response.getBody());
  }

  @Test
  @DisplayName("게시글 수정")
  void updateTalent() {
    // given
    TalentUpdateRequestDto requestDto = TalentUpdateRequestDto.builder().title("title").content("content").image("image").build();
    User user = new User("username", "1234", "good", "image", UserRoleEnum.USER);
    when(userDetails.getUser()).thenReturn(user);

    // when
    ResponseEntity<String> response = talentController.updateTalent(1L, requestDto, userDetails);

    // then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("success", response.getBody());
    verify(talentService).updateTalent(1L, requestDto, user);
  }

  @Test
  @DisplayName("게시글 삭제")
  void deleteTalent() {
    // given
    Long talentId = 1L;
    User user = new User("nickname", "username", "password", "image", UserRoleEnum.USER);
    when(userDetails.getUser()).thenReturn(user);

    // when
    talentController.deleteTalent(talentId, userDetails);

    // then
    verify(talentService, times(1)).deleteTalent(eq(talentId), eq(user));
  }
}