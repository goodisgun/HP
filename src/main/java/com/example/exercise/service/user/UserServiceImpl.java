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
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

  //유저 회원가입
  @Override
  public ResponseEntity<String> signup(UserSignupRequestDto userSignupRequestDto) {
    String password = passwordEncoder.encode(userSignupRequestDto.getPassword());

    _ck_username(userSignupRequestDto.getUsername()); // 중복검증메소드
    _ck_nickname(userSignupRequestDto.getNickname());

    User user = new User (userSignupRequestDto.getUsername(), password, userSignupRequestDto.getNickname(), "image", UserRoleEnum.USER);

    userRepository.save(user);

    return ResponseEntity.ok().build();
  }

  //관리자 회원가입
  @Override
  public ResponseEntity<String> signupAdmin(AdminSignupRequestDto adminsignupRequestDto) {
    String password = passwordEncoder.encode(adminsignupRequestDto.getPassword());

    _ck_username(adminsignupRequestDto.getUsername());
    _ck_nickname(adminsignupRequestDto.getNickname());

    if (adminsignupRequestDto.getAdminKey().equals(ADMIN_TOKEN)){
      User admin = new User(adminsignupRequestDto.getUsername(), password, adminsignupRequestDto.getNickname(), "image", UserRoleEnum.ADMIN);
      User savedAdmin = userRepository.save(admin);
      return ResponseEntity.ok("success");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 암호가 틀립니다.");
    }
  }

  //로그인
  @Override
  public String signin(UserSigninRequestDto userSigninRequestDto, HttpServletResponse response) {
    return null;
  }

  //로그아웃
  @Override
  public String signout(HttpServletRequest request, String username) {
    return null;
  }

  //관리자 로그인
  @Override
  public String signinAdmin(AdminSigninRequestDto adminSigninRequestDto,
      HttpServletResponse response) {
    return null;
  }

  // 프로필 업데이트
  @Override
  public ResponseEntity<Void> updateProfile(ProfileUpdateDto profileUpdateDto, User user) {
    _ck_nickname(profileUpdateDto.getNickname());
    user.updateProfile(profileUpdateDto);
    userRepository.save(user);
    return ResponseEntity.ok().build();
  }

  //프로필 가져오기
  @Override
  public ResponseEntity<ProfileResponseDto> getProfile(Long userId) {
    User user = _findUser(userId);
    ProfileResponseDto responseDto = ProfileResponseDto.builder()
        .nickname(user.getNickname())
        .id(user.getId())
        .image(user.getImage())
        .introduction(user.getIntroduction())
        .role(user.getUserRole().getAuthority())
        .build();
    return ResponseEntity.ok(responseDto);
  }

  //아이디 중복확인
  @Override
  public ResponseEntity<String> ck_username(UserUsernameDto userUsernameDto) {
    if (userRepository.findByUsername(userUsernameDto.getUsername()).isEmpty()) {
      return ResponseEntity.ok("success");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }
  }

  //닉네임 중복확인
  @Override
  public ResponseEntity<String> ck_nickname(UserNicknameDto userNicknameDto) {
    if (userRepository.findByNickname(userNicknameDto.getNickname()).isEmpty()) {
      return ResponseEntity.ok("success");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }
  }

  //중복코드
  private void _ck_username(String username) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
    }
  }

  //중복코드
  private void _ck_nickname(String nickname) {
    if (userRepository.findByNickname(nickname).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
    }
  }
  //중복코드
  private User _findUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new IllegalArgumentException("유저 정보가 존재하지 않습니다.")
    );
    return user;
  }
}
