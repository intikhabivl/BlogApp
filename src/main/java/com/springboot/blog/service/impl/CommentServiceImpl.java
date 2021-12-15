package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }


    @Override
    public CommentDto createComment(long post_id, CommentDto commentDto) {

        Comment comment=DtoToEntity(commentDto);
        Post post= postRepository.findById(post_id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_id));

        comment.setPost(post);
        Comment responseComment=commentRepository.save(comment);

        return mapToDto(responseComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long post_id) {

        List<Comment> comments=commentRepository.findByPost_id(post_id);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long post_id, long id) {

        Comment comment=checkAvailabilityAndBelonging(post_id, id);

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long post_id, long id, CommentDto commentDto) {

        Comment comment=checkAvailabilityAndBelonging(post_id, id);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment responseComment = commentRepository.save(comment);

        return mapToDto(responseComment);
    }

    @Override
    public void deleteCommentById(long post_id, long id) {
        Comment comment=checkAvailabilityAndBelonging(post_id, id);

        commentRepository.delete(comment);
    }


    //    Entity to DTO
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    //    DTO to  Entity
    private Comment DtoToEntity(CommentDto commentDto){
        Comment comment=new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }

//    Check for Availability of Post and Comment By respective Ids and Belonging of Comment with Post
    private Comment checkAvailabilityAndBelonging(long post_id, long id){

        Post post= postRepository.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));

        Comment comment=commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this Post");
        }
        return comment;
    }


}
