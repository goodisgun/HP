package com.example.exercise.service.user;

import com.example.exercise.dto.user.AdminSigninRequestDto;
import com.example.exercise.dto.user.AdminSignupRequestDto;
import com.example.exercise.dto.user.ProfileResponseDto;
import com.example.exercise.dto.user.ProfileUpdateDto;
import com.example.exercise.dto.user.UserNicknameDto;
import com.example.exercise.dto.user.UserSigninRequestDto;
import com.example.exercise.dto.user.UserSignupRequestDto;
import com.example.exercise.dto.user.UserUsernameDto;
import com.example.exercise.entity.RefreshToken;
import com.example.exercise.entity.SignoutAccessToken;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.jwt.JwtUtil;
import com.example.exercise.redis.CacheKey;
import com.example.exercise.repository.RefreshTokenRedisRepository;
import com.example.exercise.repository.SignoutAccessTokenRedisRepository;
import com.example.exercise.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final SignoutAccessTokenRedisRepository signoutAccessTokenRedisRepository;
    private final JwtUtil jwtUtil;


    //유저 회원가입
    @Override
    public ResponseEntity<String> signup(UserSignupRequestDto userSignupRequestDto) {
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());

        _ck_username(userSignupRequestDto.getUsername()); // 중복검증메소드
        _ck_nickname(userSignupRequestDto.getNickname());

        User user = new User(userSignupRequestDto.getUsername(), password, userSignupRequestDto.getNickname(), "image", UserRoleEnum.USER);

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    //관리자 회원가입
    @Override
    public ResponseEntity<String> signupAdmin(AdminSignupRequestDto adminsignupRequestDto) {
        String password = passwordEncoder.encode(adminsignupRequestDto.getPassword());

        _ck_username(adminsignupRequestDto.getUsername());
        _ck_nickname(adminsignupRequestDto.getNickname());

        if (adminsignupRequestDto.getAdminKey().equals(ADMIN_TOKEN)) {
            User admin = new User(adminsignupRequestDto.getUsername(), password, adminsignupRequestDto.getNickname(), "image", UserRoleEnum.ADMIN);
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 암호가 틀립니다.");
        }
    }

    //일반 로그인
    @Override
    public String signin(UserSigninRequestDto userSigninRequestDto, HttpServletResponse response) {
        loginUser(userSigninRequestDto.getUsername(), userSigninRequestDto.getPassword(), response, UserRoleEnum.USER);
        return "success";
    }

    //관리자 로그인
    @Override
    @Transactional
    public String signinAdmin(AdminSigninRequestDto adminSigninRequestDto, HttpServletResponse response) {
        loginUser(adminSigninRequestDto.getUsername(), adminSigninRequestDto.getPassword(), response, UserRoleEnum.ADMIN);
        if (!adminSigninRequestDto.getAdminKey().equals(ADMIN_TOKEN)) {
            throw new IllegalArgumentException("관리자 암호를 확인해주세요");
        }
        return "success";
    }

    //로그아웃
    @Override
    @Transactional
    @CacheEvict(value = CacheKey.USER, key = "#username")
    public String signout(HttpServletRequest request, String username) {
        String accessToken = jwtUtil.resolveToken(request);
        long remainMilliSeconds = jwtUtil.getRemainMilliSeconds(accessToken);
        refreshTokenRedisRepository.deleteById(username);
        signoutAccessTokenRedisRepository.save(SignoutAccessToken.of(accessToken, username, remainMilliSeconds));

        return "success";
    }

    //리프레시토큰
    @Override
    @Transactional
    public void refreshToken(HttpServletRequest requestForRefreshToken, HttpServletResponse responseForAccessToken) {
        try { // refresh token 추출하고 그 정보를 이용하여 user 정보를 조회
            String refreshToken = jwtUtil.resolveRefreshToken(requestForRefreshToken);
            Claims refreshInfo = jwtUtil.getUserInfoFromToken(refreshToken);

            if (refreshInfo.getSubject() != null) { // user id가 존재할 경우에만 새로운 access token 생성
                Long userId = Long.parseLong(refreshInfo.getSubject());
                User user = _findUser(userId);  // user id를 Long 타입으로 변환하고 그 id를 이용해서 user 정보를 조회

                String newAccessToken = jwtUtil.createToken(user.getUsername(), user.getUserRole()); //새로운 access token을 생성
                responseForAccessToken.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
            } else {
                throw new IllegalArgumentException("로그인 시간이 만료되었습니다. 다시 로그인 해주세요");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to refresh token", e);
        }
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

    //로그인 중복로직
    private void loginUser(String username, String password, HttpServletResponse response, UserRoleEnum userRole) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디와 비밀번호를 확인해주세요")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("아이디와 비밀번호를 확인해주세요");
        }

        if (user.getUserRole() != userRole) {
            throw new IllegalArgumentException("잘못된 권한입니다.");
        }

        String accessToken = jwtUtil.createToken(user.getUsername(), user.getUserRole());
        String refreshToken = jwtUtil.refreshToken(user.getUsername(), user.getUserRole());

        RefreshToken refreshToken1 = new RefreshToken(user.getUsername(), refreshToken.substring(8), jwtUtil.getRefreshTokenTime());
        refreshTokenRedisRepository.save(refreshToken1);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER, refreshToken);
    }
}

