package com.present.GifticBe.domain.dto;

import com.present.GifticBe.domain.Posts;
import com.present.GifticBe.domain.User;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponseDto {

    private Long id;

    private String title;

    private String content;

    private String author;
    //private UsersResponseDto author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor().getUserName();
    }

    public static List<PostsResponseDto> from(List<Posts> allPosts) {
        List<PostsResponseDto> allPostsResponseDto = new ArrayList<>();
        for(Posts posts : allPosts) {
            allPostsResponseDto.add(new PostsResponseDto(posts));
        }
        return allPostsResponseDto;
    }

}
