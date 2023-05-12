package com.present.GifticBe.domain.dto;

import com.present.GifticBe.domain.User;
import lombok.Getter;

@Getter
public class UsersResponseDto {

    private Long id;

    private String userName;

    private String userEmail;

    public UsersResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.userEmail = user.getEmail();
    }
}
