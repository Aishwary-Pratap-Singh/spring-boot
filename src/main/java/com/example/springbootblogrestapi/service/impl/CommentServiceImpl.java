package com.example.springbootblogrestapi.service.impl;

import com.example.springbootblogrestapi.entity.Comment;
import com.example.springbootblogrestapi.entity.Post;
import com.example.springbootblogrestapi.exception.ResourceNotFoundException;
import com.example.springbootblogrestapi.payload.CommentDto;
import com.example.springbootblogrestapi.repository.CommentRepository;
import com.example.springbootblogrestapi.repository.PostRepository;
import com.example.springbootblogrestapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

//        retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

//        set post to comment entity
        comment.setPost(post);

//        save comment into db
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPPostId(long postId) {
//        retrieve comment by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

//        convert list comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(comment.getId());
        comment.setName(comment.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(comment.getBody());
        return comment;
    }
}
