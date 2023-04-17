package com.example.exercise.service.talent;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.exercise.dto.talent.AllTalentResponseDto;
import com.example.exercise.dto.talent.TalentRequestDto;
import com.example.exercise.dto.talent.TalentResponseDto;
import com.example.exercise.dto.talent.TalentUpdateRequestDto;
import com.example.exercise.entity.Talent;
import com.example.exercise.entity.User;
import com.example.exercise.repository.TalentRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TalentServiceImplTest {

  @Mock
  private TalentRepository talentRepository;

  @InjectMocks
  private TalentServiceImpl talentService;

  @Test
  @DisplayName("게시글 작성")
  public void CreateTalent() {
    // given
    TalentRequestDto talentRequestDto = TalentRequestDto.builder()
        .title("제목")
        .content("안녕하세요")
        .image("test")
        .build();

    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(talentRequestDto)
        .user(user)
        .build();

    // when
    String savedTalent = talentService.createTalent(talentRequestDto, user);

    // then
    assertThat(savedTalent).isEqualTo("success");
  }

  @Test
  @DisplayName("게시글 선택조회")
  void getTalent() {
    // given
    TalentRequestDto talentRequestDto = TalentRequestDto.builder()
        .title("제목")
        .content("안녕하세요")
        .image("test")
        .build();

    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(talentRequestDto)
        .user(user)
        .build();

    when(talentRepository.findById(talent.getId())).thenReturn(Optional.of(talent));

    // when
    TalentResponseDto responseDto = talentService.getTalent(talent.getId());

    // then
    assertThat(responseDto.getTitle()).isEqualTo(talent.getTitle());
    assertThat(responseDto.getContent()).isEqualTo(talent.getContent());
    assertThat(responseDto.getImage()).isEqualTo(talent.getImage());
  }

  @Test
  @DisplayName("게시글 선택조회 - 존재하지 않는 경우")
  void getTalent_Exception() {
    // given
    Long nonExistingTalentId = 1L;
    when(talentRepository.findById(nonExistingTalentId)).thenReturn(Optional.empty());

    // when & then
    try {
      talentService.getTalent(nonExistingTalentId);
      fail("Expected TalentNotFoundException to be thrown, but it wasn't.");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(RuntimeException.class);
      assertThat(e.getMessage()).isEqualTo("게시글이 존재하지 않습니다.");
    }
  }

  @Test
  @DisplayName("전체 게시글 조회")
  void getAllTalent() {
    //given
    Talent talent1 = mock(Talent.class);
    Talent talent2 = mock(Talent.class);
    Talent talent3 = mock(Talent.class);
    User user = mock(User.class);

    List<Talent> talentList = new ArrayList<>();
    talentList.add(talent1);
    talentList.add(talent2);
    talentList.add(talent3);

    when(talent1.getUser()).thenReturn(user);
    when(talent2.getUser()).thenReturn(user);
    when(talent3.getUser()).thenReturn(user);
    when(user.getNickname()).thenReturn("user");

    //when
    when(talentRepository.findAll()).thenReturn(talentList);
    List<AllTalentResponseDto> result = talentService.getAllTalent();

    //then
    verify(talentRepository, times(1)).findAll();
    assertEquals(3,result.size());
  }

  @Test
  @DisplayName("게시글 전체조회 - 예외")
  void getAllTalent_Exception() {
    // given
    when(talentRepository.findAll()).thenReturn(Collections.emptyList());

    // then
    assertThrows(IllegalStateException.class, () -> talentService.getAllTalent());
  }

  @Test
  @DisplayName("게시글 수정 ")
  void updateTalent() {
    //given
    TalentRequestDto requestDto = TalentRequestDto.builder()
        .title("제목")
        .content("내용")
        .image("사진")
        .build();

    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user)
        .build();

    when(talentRepository.findById(talent.getId())).thenReturn(Optional.of(talent));

    TalentUpdateRequestDto updateRequestDto = TalentUpdateRequestDto.builder()
        .title("제목1")
        .content("내용1")
        .image("사진1")
        .build();

    //when
    talentService.updateTalent(talent.getId(), updateRequestDto, user);

    //then
    assertThat(updateRequestDto.getTitle()).isEqualTo(talent.getTitle());
    assertThat(updateRequestDto.getContent()).isEqualTo(talent.getContent());
    assertThat(updateRequestDto.getImage()).isEqualTo(talent.getImage());
  }

  @Test
  @DisplayName("게시글 수정 - 존재하지 않는 경우")
  void updateTalent_Null() {
    // given
    TalentRequestDto requestDto = mock(TalentRequestDto.class);
    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user)
        .build();

    TalentUpdateRequestDto updateRequestDto = mock(TalentUpdateRequestDto.class);

    lenient().when(talentRepository.findById(2L)).thenReturn(Optional.empty());

    // when&then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> talentService.updateTalent(talent.getId(), updateRequestDto, user));
    assertEquals("게시글이 존재하지 않습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("게시글 수정 - 작성자가 다른경우")
  void updateTalent_Exception() {
    // given
    TalentRequestDto requestDto = mock(TalentRequestDto.class);

    User user1 = mock(User.class);
    when(user1.getId()).thenReturn(1L);
    User user2 = mock(User.class);
    when(user2.getId()).thenReturn(2L);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user1)
        .build();

    TalentUpdateRequestDto updateRequestDto = mock(TalentUpdateRequestDto.class);

    when(talentRepository.findById(talent.getId())).thenReturn(Optional.of(talent));

    // when&then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> talentService.updateTalent(talent.getId(), updateRequestDto, user2));
    assertEquals("글 작성자만 수정이 가능합니다.", exception.getMessage());
  }


  @Test
  @DisplayName("게시글 삭제")
  void deleteTalent() {
    //given
    TalentRequestDto requestDto = mock(TalentRequestDto.class);
    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user)
        .build();

    when(talentRepository.findById(talent.getId())).thenReturn(Optional.of(talent));

    //when
    talentService.deleteTalent(talent.getId(),user);

    //then
    verify(talentRepository,times(1)).deleteById(talent.getId());
  }

  @Test
  @DisplayName("게시글 삭제 - 존재하지 않는 경우")
  void deletePost_Null() {
    // given
    TalentRequestDto requestDto = mock(TalentRequestDto.class);
    User user = mock(User.class);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user)
        .build();

    lenient().when(talentRepository.findById(2L)).thenReturn(Optional.empty());

    // when&then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> talentService.deleteTalent(talent.getId(), user));
    assertEquals("게시글이 존재하지 않습니다.", exception.getMessage());
  }

  @Test
  @DisplayName("게시글삭제 - 작성자가 다른경우")
  void deletePostAuthCheck() {
    // given
    TalentRequestDto requestDto = mock(TalentRequestDto.class);

    User user1 = mock(User.class);
    when(user1.getId()).thenReturn(1L);
    User user2 = mock(User.class);
    when(user2.getId()).thenReturn(2L);

    Talent talent = Talent.builder()
        .requestDto(requestDto)
        .user(user1)
        .build();

    when(talentRepository.findById(talent.getId())).thenReturn(Optional.of(talent));

    // when&then
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> talentService.deleteTalent(talent.getId(), user2));
    assertEquals("글 작성자만 삭제 가능합니다.", exception.getMessage());
  }
}