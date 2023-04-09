package com.present.GifticBe.controller;


import com.present.GifticBe.domain.User;
import com.present.GifticBe.domain.dto.UserJoinRequest;
import com.present.GifticBe.domain.dto.UserLoginRequest;
import com.present.GifticBe.generic.Result;
import com.present.GifticBe.repository.UserRepository;
import com.present.GifticBe.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getEmail(), dto.getPassword(), dto.getUserName());
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto, HttpServletResponse response) {
        String token = userService.login(dto.getEmail(), dto.getPassword());
        ResponseCookie cookie = ResponseCookie.from("ACCESSTOKEN", token)
                .maxAge(7 * 24 * 60 * 60) // 쿠키 만료기간 7일
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        User user = userRepository.findByEmail(dto.getEmail()).get();
        UserLoginRequest loginDto = new UserLoginRequest();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        loginDto.setId(user.getId());
        loginDto.setUserName(user.getUserName());
        loginDto.setEmail(user.getEmail());


        System.out.println(dto.getEmail());
        System.out.println(user.getEmail());

        return new ResponseEntity<>(loginDto, header, HttpStatus.OK);
    }
}
