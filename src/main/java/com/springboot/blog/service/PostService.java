package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
//import javafx.geometry.Pos;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePostById(long id);
}
