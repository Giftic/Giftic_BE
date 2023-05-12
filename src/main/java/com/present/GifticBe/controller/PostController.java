package com.present.GifticBe.controller;

import com.present.GifticBe.domain.Posts;
import com.present.GifticBe.domain.User;
import com.present.GifticBe.domain.dto.*;
import com.present.GifticBe.service.PostsService;
import com.present.GifticBe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostsService postsService;

    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/findAll")
    public List<PostsResponseDto> findAll() {
        return postsService.findAll();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/findByKeyword")
    public List<PostsResponseDto> findByKeyword(@RequestBody KeywordDto keywordDto){
        String keyword = keywordDto.getKeyword();
        System.out.println(keyword);
        return postsService.findByKeyword(keyword);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/posts/vote/{id}")
    public Long postsVote(Principal principal, @PathVariable("id") Long id) {
        PostsResponseDto postsResponseDto = this.postsService.findById(id);
        User user = this.userService.getUser(id);
        return id;
    }
}
