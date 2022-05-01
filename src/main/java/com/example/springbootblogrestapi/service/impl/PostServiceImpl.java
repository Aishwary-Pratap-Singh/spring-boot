package com.example.springbootblogrestapi.service.impl;

import com.example.springbootblogrestapi.entity.Post;
import com.example.springbootblogrestapi.payload.PostDto;
import com.example.springbootblogrestapi.repository.PostRepository;
import com.example.springbootblogrestapi.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

//    convert entity to dto
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

//    convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;

    }
    @Override
    public PostDto createPost(PostDto postDto) {

//        convert dto to entity
//        saving content in db
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);


//        convert entity to dto
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;

    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }
}
