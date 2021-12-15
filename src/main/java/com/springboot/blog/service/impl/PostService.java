package com.springboot.blog.service.impl;

import com.springboot.blog.payload.PostDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
//import javafx.geometry.Pos;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePostById(long id);
}
