package com.present.GifticBe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String email;
    private String name;
    private String nickname;
}
