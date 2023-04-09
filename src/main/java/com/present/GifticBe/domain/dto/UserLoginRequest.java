package com.present.GifticBe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserLoginRequest {
    private Long id;
    private String email;
    private String userName;
    private String password;

    public UserLoginRequest(Long id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }
}
