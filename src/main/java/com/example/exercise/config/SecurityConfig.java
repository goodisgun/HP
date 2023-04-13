package com.example.exercise.config;

import com.example.exercise.jwt.JwtAuthFilter;
import com.example.exercise.jwt.JwtUtil;
import com.example.exercise.repository.RefreshTokenRedisRepository;
import com.example.exercise.repository.SignoutAccessTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // 메서드 보안 활성화함. @Secured 사용할 수 있게 함.
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    private final SignoutAccessTokenRedisRepository signoutAccessTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";


    @Bean
    public PasswordEncoder passwordEncoder() { //비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 자원에 대한 요청 무시함
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll() // OPTIONS 메서드 요청 허용함
                .requestMatchers("/signup").permitAll() // 회원 가입 요청 허용함
                .requestMatchers("/signin").permitAll() // 로그인 요청 허용함
                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** 경로에 대해 ADMIN 권한 필요함
                .anyRequest().authenticated() // 그 외의 요청은 인증 필요함
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil,  signoutAccessTokenRedisRepository, refreshTokenRedisRepository),
                        UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 생성 방식을 STATELESS로 설정함
        http.formLogin().disable();

        return http.build();

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Authorization")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOrigins("","");
    }
}




