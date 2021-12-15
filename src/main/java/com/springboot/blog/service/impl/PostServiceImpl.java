package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=DtoToEntity(postDto);
        Post uploadedPost= postRepository.save(post);
        PostDto postDtoResponse = mapToDto(uploadedPost);

        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts=postRepository.findAll(pageable);
        List<Post> listOfPosts=posts.getContent();
        List<PostDto> responseContent= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();

        postResponse.setContent(responseContent);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {

        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost=postRepository.save(post);

        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) {

        Post post= postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    //    Entity to DTO
    private PostDto mapToDto(Post post){
        PostDto postDto=new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getDescription());

        return postDto;
    }

//    DTO to  Entity
    private Post DtoToEntity(PostDto postDto){
        Post post=new Post();

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getDescription());

        return post;
    }
}
