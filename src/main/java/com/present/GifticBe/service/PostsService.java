package com.present.GifticBe.service;

import com.present.GifticBe.domain.Posts;
import com.present.GifticBe.domain.User;
import com.present.GifticBe.domain.dto.PostsResponseDto;
import com.present.GifticBe.domain.dto.PostsSaveRequestDto;
import com.present.GifticBe.domain.dto.PostsUpdateRequestDto;
import com.present.GifticBe.repository.PostRepository;
import com.present.GifticBe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        User user = userRepository.findUserById(requestDto.getAuthor()).get();
        Posts posts = Posts.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(user)
                .build();
        postRepository.save(posts);
        return posts.getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    public List<PostsResponseDto> findAll() {
        return PostsResponseDto.from(postRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        postRepository.delete(posts);
    }

    public List<PostsResponseDto> findByKeyword(String keyword) {
        System.out.println("keyword : " + keyword);
        List<Posts> search = postRepository.search(keyword);
        System.out.println("searchSize : " + search.size());

        return PostsResponseDto.from(search);
    }
}
