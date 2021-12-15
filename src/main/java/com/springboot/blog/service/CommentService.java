package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long post_id, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long post_id);

    CommentDto getCommentById(long post_id, long id);

    CommentDto updateCommentById(long post_id, long id, CommentDto commentDto);

    void deleteCommentById(long post_id, long id);
}
