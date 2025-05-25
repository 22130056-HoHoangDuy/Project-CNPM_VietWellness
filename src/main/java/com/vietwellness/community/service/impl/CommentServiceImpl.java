// CommentServiceImpl.java
package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Comment;
import com.vietwellness.community.repository.CommentRepository;
import com.vietwellness.community.service.CommentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @Override
    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
