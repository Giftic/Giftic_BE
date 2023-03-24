package com.present.GifticBe.controller;


import com.present.GifticBe.domain.dto.UserJoinRequest;
import com.present.GifticBe.domain.dto.UserLoginRequest;
import com.present.GifticBe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {
        userService.join(dto.getEmail(), dto.getPassword(), dto.getUserName());
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
