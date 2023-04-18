package com.example.exercise.controller;
import com.example.exercise.dto.user.*;
import com.example.exercise.security.UserDetailsImpl;
import com.example.exercise.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  //회원가입-유저
  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody @Valid UserSignupRequestDto requestDto) {
    return userService.signup(requestDto);
  }

  //회원가입-관리자
  @PostMapping("/admin/signup")
  public ResponseEntity<String> signupAdmin(@RequestBody @Valid AdminSignupRequestDto requestDto){
    return userService.signupAdmin(requestDto);
  }

// 로그인-일반
@PostMapping("/signin")
public ResponseEntity<String> signin(@RequestBody UserSigninRequestDto requestDto, HttpServletResponse response) {
  return ResponseEntity.status(HttpStatus.OK).body(userService.signin(requestDto,response));
}
//로그인 - 관리자
@PostMapping("/admin/signin")
public ResponseEntity<String> adminSignin(@RequestBody AdminSigninRequestDto requestDto, HttpServletResponse response) {
  return ResponseEntity.status(HttpStatus.OK).body(userService.signinAdmin(requestDto,response));
}

// 로그아웃
@PostMapping("/signout")
public ResponseEntity<String> signout(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
  return ResponseEntity.status(HttpStatus.OK).body(userService.signout(request,userDetails.getUsername()));
}

  // 프로필 수정
  @PatchMapping("/profiles")
  public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateDto updateDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
    return userService.updateProfile(updateDto, userDetails.getUser());
  }

  //프로필 조회
  @GetMapping("/profiles/{userId}")
  public ResponseEntity<ProfileResponseDto> getProfile(Long userId){
    return userService.getProfile(userId);
  }

  //아이디 중복 체크
  @PostMapping("/check-username")
  public ResponseEntity<String> ck_id(@RequestBody UserUsernameDto userUsernameDto) {
    return userService.ck_username(userUsernameDto);
  }

  //닉네임 중복 검증
  @PostMapping("/check-nickname")
  public ResponseEntity<String> ck_nickname(@RequestBody UserNicknameDto userNicknameDto) {
    return userService.ck_nickname(userNicknameDto);
  }

}
