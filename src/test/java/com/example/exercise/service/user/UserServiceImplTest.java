package com.example.exercise.service.user;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import java.util.Optional;

import com.example.exercise.dto.user.*;
import com.example.exercise.entity.SignoutAccessToken;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.jwt.JwtUtil;
import com.example.exercise.repository.RefreshTokenRedisRepository;
import com.example.exercise.repository.SignoutAccessTokenRedisRepository;
import com.example.exercise.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


//@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    SignoutAccessTokenRedisRepository signoutAccessTokenRedisRepository;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshTokenRedisRepository refreshTokenRedisRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    private UserSignupRequestDto createUserSignupRequestDto() {
        return UserSignupRequestDto.builder()
                .username("hyenzzang")
                .password("qwer123")
                .nickname("오리")
                .build();
    }

    @Test
    @DisplayName("유저회원가입")
     void userSignup() {
        // given
        UserSignupRequestDto requestDto = createUserSignupRequestDto();
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("password");
        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(Optional.empty());

        // when
        ResponseEntity<String> responseEntity = userService.signup(requestDto);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(argThat(user ->
                user.getUsername().equals(requestDto.getUsername()) &&
                        user.getPassword().equals("password") &&
                        user.getNickname().equals(requestDto.getNickname()) &&
                        user.getUserRole().equals(UserRoleEnum.USER)));
    }

    @Test
    @DisplayName("유저회원가입_유저네임중복")
    void userSignupDuplicatedUsername() {
        //given
        UserSignupRequestDto requestDto = createUserSignupRequestDto();
        User user = new User(requestDto.getUsername(), "password", requestDto.getNickname(), "image",
                UserRoleEnum.USER);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("password");
        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.of(user));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(requestDto));

        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }

    @Test
    @DisplayName("유저회원가입_닉네임중복")
    public void userSignupDuplicatedNickname() {
        //given
        UserSignupRequestDto requestDto = createUserSignupRequestDto();
        User user = new User(requestDto.getUsername(), "password", requestDto.getNickname(), "image", UserRoleEnum.USER);
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("password");
        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(Optional.of(user));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(requestDto));

        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 닉네임입니다.");
    }

//    @Test
//    @DisplayName("유저로그인")
//    void signin() {
//        // given
//       UserSigninRequestDto userSigninRequestDto = UserSigninRequestDto.builder().username("user").password("password").build();
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        User user = new User("user", passwordEncoder.encode("password"), "nickname", "image", UserRoleEnum.USER);
//        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
//        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
//        when(jwtUtil.createToken("user", UserRoleEnum.USER)).thenReturn("accessToken");
//        when(jwtUtil.refreshToken("user", UserRoleEnum.USER)).thenReturn("refreshToken");
//        when(jwtUtil.getRefreshTokenTime()).thenReturn(1000L);
//
//        // when
//        String result = userService.signin(userSigninRequestDto, response);
//
//        // then
//        assertEquals("success", result);
//        verify(response).addHeader(JwtUtil.AUTHORIZATION_HEADER, "accessToken");
//        verify(response).addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER, "refreshToken");
//        verify(refreshTokenRedisRepository).save(argThat(refreshToken1 ->
//                refreshToken1.getRefreshToken().equals("token") &&
//                refreshToken1.getId().equals("user") &&
//                refreshToken1.getExpiration().equals(1000L)));
//    }

//    @Test
//    @DisplayName("유저로그인")
//    public void userSignin() {
//        // given
//        UserSigninRequestDto requestDto = UserSigninRequestDto.builder().username("hyenzzang").password("qwer123").build();
//        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), "오리", "image", UserRoleEnum.USER);
//        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.of(user));
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        // when
//        String result = userService.signin(requestDto, response);
//
//        // then
//        assertThat(result).isEqualTo("success");
//        assertThat(response.getCookie("accessToken")).isNotNull();
//        assertThat(Objects.requireNonNull(response.getCookie("accessToken")).getMaxAge()).isEqualTo(JwtTokenProvider.ACCESS_TOKEN_EXPIRE_TIME);
//        assertThat(response.getCookie("refreshToken")).isNotNull();
//        assertThat(Objects.requireNonNull(response.getCookie("refreshToken")).getMaxAge()).isEqualTo(JwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME);
//    }


    @Test
    @DisplayName("유저로그인_아이디오류")
    void signinWrongUsername() {
        // given
        UserSigninRequestDto userSigninRequestDto = UserSigninRequestDto.builder().username("user1").password("password").build();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.signin(userSigninRequestDto, response));
    }

    @Test
    @DisplayName("유저로그인_비밀번호오류")
    void signinWrongPassword() {
        // given
        UserSigninRequestDto userSigninRequestDto = UserSigninRequestDto.builder().username("user").password("password").build();
        HttpServletResponse response = mock(HttpServletResponse.class);
        User user = new User("user", passwordEncoder.encode("password"), "nickname", "image", UserRoleEnum.USER);
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.signin(userSigninRequestDto, response));
    }

//    @Test
//    @DisplayName("관리자회원가입")
//    void adminSignup() {
//        //given
//        AdminSignupRequestDto requestDto = AdminSignupRequestDto.builder()
//                .username("admin")
//                .password("admin123!!")
//                .nickname("꽥꽥 관리자")
//                .adminKey("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
//                .build();
//        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("password");
//        when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
//        when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(Optional.empty());
//
//        //when
//        ResponseEntity<String> response = userService.signupAdmin(requestDto);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("success", response.getBody());
//        verify(userRepository).save(argThat(user ->
//                user.getUsername().equals("admin") &&
//                user.getPassword().equals("admin123!!") &&
//                user.getNickname().equals("꽥꽥 관리자") &&
//                user.getUserRole().equals(UserRoleEnum.USER)));
//    }

    @Test
    @DisplayName("관리자회원가입_관리자암호오류")
    void adminSignupWrongAdminKey() {
        // given
        AdminSignupRequestDto requestDto = AdminSignupRequestDto.builder()
                .username("admin")
                .password("admin123")
                .nickname("admin")
                .adminKey("wrong_token")
                .build();

        // when
        ResponseEntity<String> result = userService.signupAdmin(requestDto);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("관리자 암호가 틀립니다.", result.getBody());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("관리자로그인_아이디오류")
    void adminSigninWrongUsername() {
        // given
        AdminSigninRequestDto adminSigninRequestDto = AdminSigninRequestDto.builder().username("admin").password("password").build();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.signinAdmin(adminSigninRequestDto, response));
    }

    @Test
    @DisplayName("관리자로그인_비밀번호오류")
    void adminSigninWrongPassword() {
        // given
        AdminSigninRequestDto adminSigninRequestDto = AdminSigninRequestDto.builder().username("admin").password("password").build();
        HttpServletResponse response = mock(HttpServletResponse.class);

        User user = new User("admin", passwordEncoder.encode("password"), "nickname", "image", UserRoleEnum.ADMIN);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> userService.signinAdmin(adminSigninRequestDto, response));
    }

//    @Test
//    @DisplayName("관리자로그인_어드민오류")
//    void signinAdminWrongAdminKey() {
//        // given
//        String username = "admin";
//        String password = "admin123!!";
//        String adminKey = "wrongAdminKey";
//
//        AdminSigninRequestDto adminSigninRequestDto = AdminSigninRequestDto.builder()
//                .username(username)
//                .password(password)
//                .adminKey(adminKey)
//                .build();
//        HttpServletResponse response = mock(HttpServletResponse.class);
//
//        User admin = User.builder()
//                .username(username)
//                .password(passwordEncoder.encode(password))
//                .build();
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(admin));
//
//        // when
//        Throwable thrown = catchThrowable(() -> userService.signinAdmin(adminSigninRequestDto, response));
//
//        // then
//        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
//                .hasMessageContaining("관리자 암호를 확인해주세요");
//        verify(response, never()).addHeader(JwtUtil.AUTHORIZATION_HEADER, "accessToken");
//        verify(response, never()).addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER, "refreshToken");
//        verify(refreshTokenRedisRepository, never()).save(any(RefreshToken.class));
//    }

    @Test
    @DisplayName("로그아웃")
    void signout() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        String accessToken = "accessToken";
        String username = "user";
        Long time = 1L;
        when(jwtUtil.resolveToken(request)).thenReturn(accessToken);
        when(jwtUtil.getRemainMilliSeconds(any(String.class))).thenReturn(time);

        //when
        String result = userService.signout(request,username);

        //then
        assertEquals("success",result);
        verify(refreshTokenRedisRepository).deleteById(username);
        verify(signoutAccessTokenRedisRepository).save(any(SignoutAccessToken.class));
    }

    @Test
    @DisplayName("프로필수정")
    void updateProfile() {
        // given
        ProfileUpdateDto profileUpdateDto = ProfileUpdateDto.builder()
                .nickname("빙봉")
                .introduction("소개입니다")
                .image("사진을 넣어요")
                .build();

        User user = User.builder()
                .id(1L)
                .username("hyenzzang")
                .userRole(UserRoleEnum.USER)
                .nickname("오리")
                .image("")
                .build();

        when(userRepository.save(user)).thenReturn(user);

        // when
        ResponseEntity<Void> response = userService.updateProfile(profileUpdateDto, user);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("빙봉", user.getNickname());
        assertEquals("소개입니다", user.getIntroduction());
        assertEquals("사진을 넣어요", user.getImage());
        verify(userRepository, times(1)).save(user);
    }

//    @Test
//    @DisplayName("유저 프로필 조회")
//    void testGetProfile() {
//        // given
//        Long userId = 1L;
//        User user = User.builder()
//                .username("hyenzzang")
//                .nickname("오리")
//                .image("사진")
//                .userRole(UserRoleEnum.USER)
//                .build();
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//        // when
//        ResponseEntity<ProfileResponseDto> response = userService.getProfile(userId);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user.getNickname(), response.getBody().getNickname());
//        assertEquals(user.getId(), response.getBody().getId());
//        assertEquals(user.getImage(), response.getBody().getImage());
//        assertEquals(user.getIntroduction(), response.getBody().getIntroduction());
//        assertEquals(user.getUserRole().getAuthority(), response.getBody().getRole());
//    }





}

