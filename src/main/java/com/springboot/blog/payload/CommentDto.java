package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class CommentDto {

    private Long id;
    private String name;
    private String email;
    private String body;
    private Post post;
}
