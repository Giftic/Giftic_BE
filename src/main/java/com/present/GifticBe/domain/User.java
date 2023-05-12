package com.present.GifticBe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String userName;

    private String grade;

    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "donate_user", cascade = CascadeType.ALL)
    private List<Donate> donateList = new ArrayList<>();

    @Builder
    public User(String email, String password, String userName, String grade, LocalDateTime joinDate) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.grade = grade;
        this.joinDate = joinDate;
    }

    public User(String email, String password, List<GrantedAuthority> authorities) {
    }
}
