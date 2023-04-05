package com.example.exercise.controller;
import com.example.exercise.dto.user.AdminSignupRequestDto;
import com.example.exercise.dto.user.ProfileResponseDto;
import com.example.exercise.dto.user.ProfileUpdateDto;
import com.example.exercise.dto.user.UserNicknameDto;
import com.example.exercise.dto.user.UserSignupRequestDto;
import com.example.exercise.dto.user.UserUsernameDto;
import com.example.exercise.entity.User;
import com.example.exercise.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto requestDto) {
    return userService.signup(requestDto);
  }

  @PostMapping("/admin/signup")
  public ResponseEntity<String> signupAdmin(@RequestBody AdminSignupRequestDto requestDto){
    return userService.signupAdmin(requestDto);
  }

//  @PostMapping("/signin")
//
//  @PostMapping("/adimin/signin")
//
//  @PostMapping("/signout")

  @PatchMapping("/profiles")
  public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateDto updateDto, User user){
    return userService.updateProfile(updateDto, user);
  }

  @GetMapping("/profiles/{userId}")
  public ResponseEntity<ProfileResponseDto> getProfile(Long userId){
    return userService.getProfile(userId);
  }

  @PostMapping("/check-username")
  public ResponseEntity<String> ck_id(@RequestBody UserUsernameDto userUsernameDto) {
    return userService.ck_username(userUsernameDto);
  }

  @PostMapping("/check-nickname")
  public ResponseEntity<String> ck_nickname(@RequestBody UserNicknameDto userNicknameDto) {
    return userService.ck_nickname(userNicknameDto);
  }

}
