package com.example.exercise.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.exercise.dto.gathering.AllGatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringRequestDto;
import com.example.exercise.dto.gathering.GatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;
import com.example.exercise.entity.Gathering;
import com.example.exercise.entity.User;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.gathering.GatheringService;
import com.example.exercise.service.gathering.GatheringServiceImpl;
import java.util.ArrayList;
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
public class GatheringControllerTest {

  @Mock
  private GatheringServiceImpl gatheringService;

  @Mock
  private UserDetailsImpl userDetails;

  @InjectMocks
  private GatheringController gatheringController;


  @Test
  @DisplayName("게시물 생성")
  void createGathering() {
    GatheringRequestDto requestDto = GatheringRequestDto.builder()
        .title("test title")
        .content("test content")
        .image("test image")
        .gatheringTime("2023-04-20 12:00:00")
        .maxEnrollmentCount(10)
        .build();
    when(userDetails.getUser()).thenReturn(new User());
    when(gatheringService.createGathering(requestDto, userDetails.getUser()))
        .thenReturn(new GatheringResponseDto(
            1L,
            "test title",
            "test content",
            "test image",
            "2023-04-20 12:00:00",
            10,
            "user1",
            new ArrayList<>()
        ));

    GatheringController gatheringController = new GatheringController(gatheringService);

    ResponseEntity<GatheringResponseDto> responseEntity =
        gatheringController.createGathering(requestDto, userDetails);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(GatheringResponseDto.class, responseEntity.getBody().getClass());
  }


  @Test
  @DisplayName("게시물 수정")
  void updateGathering() {
    // Given
    Long gatheringId = 1L;
    GatheringUpdateRequestDto updateRequestDto = GatheringUpdateRequestDto.builder()
        .title("updated title")
        .content("updated content")
        .image("updated image")
        .gatheringTime("2023-04-21 12:00:00")
        .maxEnrollmentCount(20)
        .build();
    when(userDetails.getUser()).thenReturn(new User());
    when(gatheringService.updateGathering(eq(gatheringId), any(User.class), eq(updateRequestDto)))
        .thenReturn(new GatheringResponseDto(
            gatheringId,
            "good",
            "updated title",
            "updated image",
            "updated content",
            20,
            "2023-04-21 12:00:00",
            new ArrayList<>()
        ));

    // When
    ResponseEntity<GatheringResponseDto> responseEntity =
        gatheringController.updateGathering(gatheringId, userDetails, updateRequestDto);

    // Then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(GatheringResponseDto.class, responseEntity.getBody().getClass());
    assertEquals(gatheringId, responseEntity.getBody().getId());
    assertEquals("updated title", responseEntity.getBody().getTitle());
    assertEquals("updated content", responseEntity.getBody().getContent());
    assertEquals("updated image", responseEntity.getBody().getImage());
    assertEquals("2023-04-21 12:00:00", responseEntity.getBody().getGatheringTime());
    assertEquals(20, responseEntity.getBody().getMaxEnrollmentCount());
    assertEquals(new ArrayList<>(), responseEntity.getBody().getComments());
  }

  @Test
  @DisplayName("게시물 삭제")
  void deleteGathering() {
    Long gatheringId = 1L;
    when(userDetails.getUser()).thenReturn(new User());

    GatheringController gatheringController = new GatheringController(gatheringService);

    gatheringController.deleteGathering(gatheringId, userDetails);

    verify(gatheringService, times(1)).deleteGathering(gatheringId, userDetails.getUser());
  }

  @Test
  @DisplayName("모임 게시글 전체 조회")
  void getAllGathering() {
//    User user1 = mock(User.class);
//    User user2 = mock(User.class);
//
//    List<AllGatheringResponseDto> allGatheringList = new ArrayList<>();
//    allGatheringList.add(new AllGatheringResponseDto(
//        Gathering.builder()
//            .requestDto(GatheringRequestDto.builder()
//                .title("title1")
//                .content("content1")
//                .image("image1")
//                .gatheringTime("2023-04-20 12:00:00")
//                .maxEnrollmentCount(10)
//                .build())
//            .user(user1)
//            .build()));
//    allGatheringList.add(new AllGatheringResponseDto(
//        Gathering.builder()
//            .requestDto(GatheringRequestDto.builder()
//                .title("title2")
//                .content("content2")
//                .image("image2")
//                .gatheringTime("2023-04-20 13:00:00")
//                .maxEnrollmentCount(5)
//                .build())
//            .user(user2)
//            .build()));
//
//    when(gatheringService.getAllGathering()).thenReturn(allGatheringList);
//
//    GatheringController gatheringController = new GatheringController(gatheringService);
//
//    ResponseEntity<List<AllGatheringResponseDto>> responseEntity = gatheringController.getAllGathering();
//
//    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    assertEquals(allGatheringList, responseEntity.getBody());
  }
  @Test
  @DisplayName("게시물 선택조회")
  void getGathering() {
    GatheringResponseDto gatheringResponseDto = GatheringResponseDto.builder()
        .id(1L)
        .title("title")
        .content("content")
        .image("image")
        .gatheringTime("2023-04-20 12:00:00")
        .maxEnrollmentCount(10)
        .build();

    when(gatheringService.getGathering(anyLong())).thenReturn(gatheringResponseDto);

    GatheringController gatheringController = new GatheringController(gatheringService);

    ResponseEntity<GatheringResponseDto> responseEntity = gatheringController.getGathering(1L);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(gatheringResponseDto, responseEntity.getBody());
  }
}