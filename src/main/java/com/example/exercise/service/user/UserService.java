package com.example.exercise.service.user;

import com.example.exercise.dto.user.AdminSigninRequestDto;
import com.example.exercise.dto.user.AdminSignupRequestDto;
import com.example.exercise.dto.user.ProfileResponseDto;
import com.example.exercise.dto.user.ProfileUpdateDto;
import com.example.exercise.dto.user.UserNicknameDto;
import com.example.exercise.dto.user.UserSigninRequestDto;
import com.example.exercise.dto.user.UserSignupRequestDto;
import com.example.exercise.dto.user.UserUsernameDto;
import com.example.exercise.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

  //회원가입
   ResponseEntity<String> signup(UserSignupRequestDto userSignupRequestDto);

  //관리자 회원가입
  ResponseEntity<String> signupAdmin(AdminSignupRequestDto adminsignupRequestDto);

  //로그인
  String signin(UserSigninRequestDto userSigninRequestDto, HttpServletResponse response);

  //로그아웃
  String signout(HttpServletRequest request,String username);

  //리프레시 토큰
  void refreshToken(HttpServletRequest request, HttpServletResponse response);

  //관리자 로그인
  String signinAdmin(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response);

  //프로필 수정
   ResponseEntity<Void> updateProfile(ProfileUpdateDto profileUpdateDto, User user);

  //프로필 조회
 ResponseEntity<ProfileResponseDto> getProfile(Long userId);

  //아이디 중복 조회
  ResponseEntity<String> ck_username (UserUsernameDto userUsernameDto);

  //닉네임 중복 조회
  ResponseEntity<String> ck_nickname (UserNicknameDto userNicknameDto);

}
