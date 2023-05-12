package com.present.GifticBe.service;

import com.present.GifticBe.domain.User;
import com.present.GifticBe.domain.dto.UserRole;
import com.present.GifticBe.exception.AppException;
import com.present.GifticBe.exception.ErrorCode;
import com.present.GifticBe.repository.UserRepository;
import com.present.GifticBe.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String key;

    private Long expireTimeMs = 1000 * 60 * 60L;

    public String join(String email, String password, String userName) {

        // username 중복 체크
        userRepository.findByEmail(email)
                .ifPresent(users -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, email + "는 이미 있습니다.");
                });

        // 저장
        User user = User.builder()
                .email(email)
                .userName(userName)
                .password(encoder.encode(password))
                .joinDate(LocalDateTime.now())
                .grade("BRONZE")
                .build();

        userRepository.save(user);

        return "SUCCESS";
    }

    public String login(String email, String password) {

        /**
         * userName 없음
         */
        User selectedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, email + "이 없습니다."));

        /**
         * password 틀림
         */
        log.info("selectedPw:{} pw:{}", selectedUser.getPassword(), password);
        if(!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 했습니다.");
        }

        /**
         * 앞에서 Exception 나지 않았으면 토큰 발행 및 반환
         */
        String token = JwtTokenUtil.createToken(selectedUser.getEmail(), key, expireTimeMs);

        return token;
    }

    public User getUser(Long id) {
        Optional<User> user = this.userRepository.findUserById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND, "해당 유저가 존재하지 않습니다.");
        }
    }
}
