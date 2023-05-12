package com.present.GifticBe.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    @OneToOne(mappedBy = "posts", cascade = CascadeType.ALL)
    private Donate donate;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    private Set<User> voter;

    @Builder
    public Posts(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
