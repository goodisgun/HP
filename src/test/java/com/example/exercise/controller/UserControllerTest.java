package com.example.exercise.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.exercise.dto.user.ProfileResponseDto;
import com.example.exercise.dto.user.UserNicknameDto;
import com.example.exercise.dto.user.UserUsernameDto;
import org.junit.jupiter.api.Assertions;
import com.example.exercise.dto.user.AdminSigninRequestDto;
import com.example.exercise.dto.user.AdminSignupRequestDto;
import com.example.exercise.dto.user.ProfileUpdateDto;
import com.example.exercise.dto.user.UserSigninRequestDto;
import com.example.exercise.dto.user.UserSignupRequestDto;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private UserController userController;


  @Autowired
  private HttpServletResponse response;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userController = new UserController(userService);
  }


  @Test
  @DisplayName("유저 회원가입")
  void testSignup() throws Exception {
    UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
        .username("good")
        .password("1234")
        .build();

    when(userService.signup(any(UserSignupRequestDto.class)))
        .thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

    ResponseEntity<String> response = userController.signup(requestDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("success", response.getBody());
  }

  @Test
  @DisplayName("관리자 회원가입")
  void signupAdmin() throws Exception {
    AdminSignupRequestDto requestDto = AdminSignupRequestDto.builder()
        .username("admin")
        .password("1234")
        .adminKey("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
        .build();

    when(userService.signupAdmin(requestDto))
        .thenReturn(new ResponseEntity<>("success", HttpStatus.OK));

    ResponseEntity<String> response = userController.signupAdmin(requestDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("success", response.getBody());
  }

  @Test
  @DisplayName("유저 로그인")
  void Signin() throws Exception {
    // given
    UserSigninRequestDto requestDto = UserSigninRequestDto.builder()
        .username("good")
        .password("1234")
        .build();

    HttpServletResponse response = mock(HttpServletResponse.class);
    when(userService.signin(requestDto, response))
        .thenReturn("success");

    // when
    ResponseEntity<String> responseEntity = userController.signin(requestDto, response);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("success", responseEntity.getBody());
  }

  @Test
  @DisplayName("관리자 로그인")
  void adminSignin() throws Exception {
    // given
    String username = "admin";
    String password = "password";
    String adminKey = "secret";

    AdminSigninRequestDto requestDto = AdminSigninRequestDto.builder()
        .username(username)
        .password(password)
        .adminKey(adminKey)
        .build();

    // when
    when(userService.signinAdmin(requestDto, response))
        .thenReturn("Admin signed in successfully!");

    ResponseEntity<String> responseEntity = userController.adminSignin(requestDto, response);

    // then
    verify(userService).signinAdmin(requestDto, response);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Admin signed in successfully!", responseEntity.getBody());
  }

  @Test
  @DisplayName("로그아웃")
  void signout() throws Exception {
//    // Given
//    String username = "good";
//    UserDetailsImpl userDetails = new UserDetailsImpl(User.builder().username(username).build());
//    MockHttpServletRequest request = new MockHttpServletRequest();
//
//    // When
//    when(userService.signout(request, username)).thenReturn("User signed out successfully!");
//
//    ResponseEntity<String> responseEntity = userController.signout(request, userDetails);
//
//    // Then
//    verify(userService).signout(request, username);
//    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    assertEquals("User signed out successfully!", responseEntity.getBody());
  }

  @Test
  @DisplayName("프로필 수정")
  void updateProfile() throws Exception {
    // Given
    ProfileUpdateDto updateDto = ProfileUpdateDto.builder()
        .nickname("good")
        .introduction("hi")
        .image("image")
        .build();

    User user = User.builder()
        .id(1L)
        .username("user")
        .password("1234")
        .nickname("gun")
        .userRole(UserRoleEnum.USER)
        .build();

    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);

    ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();
    Mockito.when(userService.updateProfile(updateDto, user)).thenReturn(expectedResponse);

    // When
    ResponseEntity<Void> actualResponse = userController.updateProfile(updateDto, (UserDetailsImpl) authentication.getPrincipal());

    // Then
    Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    Mockito.verify(userService, Mockito.times(1)).updateProfile(updateDto, user);
  }


  @Test
  @DisplayName("프로필 조회")
  void getProfile() {
    // Given
    Long userId = 1L;
    ProfileResponseDto expectedProfileResponseDto = ProfileResponseDto.builder()
        .nickname("good")
        .introduction("hi")
        .image("image")
        .build();
    ResponseEntity<ProfileResponseDto> expectedResponseEntity = ResponseEntity.ok(expectedProfileResponseDto);
    Mockito.when(userService.getProfile(userId)).thenReturn(expectedResponseEntity);

    // When
    ResponseEntity<ProfileResponseDto> actualResponseEntity = userController.getProfile(userId);

    // Then
    assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    assertEquals(expectedProfileResponseDto.getNickname(), actualResponseEntity.getBody().getNickname());
    assertEquals(expectedProfileResponseDto.getIntroduction(), actualResponseEntity.getBody().getIntroduction());
    assertEquals(expectedProfileResponseDto.getImage(), actualResponseEntity.getBody().getImage());

    Mockito.verify(userService, Mockito.times(1)).getProfile(userId);
  }

  @Test
  @DisplayName("아이디 중복 체크")
  void ck_username() {
    // Given
    UserUsernameDto userUsernameDto = new UserUsernameDto("duplicated");

    Mockito.when(userService.ck_username(userUsernameDto)).thenReturn(ResponseEntity.badRequest().build());

    // When
    ResponseEntity<String> response = userController.ck_id(userUsernameDto);

    // Then
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  @DisplayName("아이디 중복 체크 - 중복되지 않은 아이디")
  void ck_username_exception() {
    // Given
    UserUsernameDto userUsernameDto = new UserUsernameDto("not_duplicated");

    Mockito.when(userService.ck_username(userUsernameDto)).thenReturn(ResponseEntity.ok().build());

    // When
    ResponseEntity<String> response = userController.ck_id(userUsernameDto);

    // Then
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  @DisplayName("닉네임 검증")
  void ck_nickname() {
    // Given
    UserNicknameDto userNicknameDto = new UserNicknameDto("duplicated");

    Mockito.when(userService.ck_nickname(userNicknameDto)).thenReturn(ResponseEntity.badRequest().build());

    // When
    ResponseEntity<String> response = userController.ck_nickname(userNicknameDto);

    // Then
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  @DisplayName("아이디 중복 체크 - 중복되지 않은 아이디")
  void ck_nickname_exception() {
    // Given
    UserNicknameDto userNicknameDto = new UserNicknameDto("not_duplicated");

    Mockito.when(userService.ck_nickname(userNicknameDto)).thenReturn(ResponseEntity.ok().build());

    // When
    ResponseEntity<String> response = userController.ck_nickname(userNicknameDto);

    // Then
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

}