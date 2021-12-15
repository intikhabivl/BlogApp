package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts/{post_id}/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable("post_id") long post_id,
                                                    @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(post_id, commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommentDto> getCommentsByPostId(@PathVariable("post_id") long post_id){
        return new ResponseEntity(commentService.getCommentsByPostId(post_id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("post_id") long post_id,
                                                     @PathVariable("id") long id){

        return new ResponseEntity(commentService.getCommentById(post_id, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("post_id") long post_id,
                                                        @PathVariable("id") long id,
                                                        @RequestBody CommentDto commentDto){
        return new ResponseEntity(commentService.updateCommentById(post_id, id, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("post_id") long post_id,
                                                    @PathVariable("id") long id){
        commentService.deleteCommentById(post_id, id);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }
}
