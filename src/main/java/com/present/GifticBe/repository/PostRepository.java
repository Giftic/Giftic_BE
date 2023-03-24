package com.present.GifticBe.repository;

import com.present.GifticBe.domain.Posts;
import com.present.GifticBe.domain.User;
import com.present.GifticBe.domain.dto.PostsResponseDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAll();

    @Query("SELECT p FROM Posts p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Posts> search(@Param("keyword") String keyword);


//    List<PostsResponseDto> findAll(Specification<PostsResponseDto> spec, Sort by);
}
