package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Comment;
import com.vietwellness.community.repository.CommentRepository;
import com.vietwellness.community.service.CommentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của CommentService.
 * Chịu trách nhiệm xử lý nghiệp vụ liên quan đến Comment.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    /**
     * Constructor để inject CommentRepository.
     * @param commentRepository repository thao tác với Comment
     */
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    /**
     * Tạo mới một Comment và lưu vào database.
     * @param comment đối tượng Comment cần tạo
     * @return Comment đã được lưu
     */
    @Override
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    /**
     * Lấy Comment theo id.
     * @param id id của Comment
     * @return Optional chứa Comment nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả Comment.
     * @return danh sách Comment
     */
    @Override
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    /**
     * Cập nhật Comment (thực chất là lưu lại Comment mới có id tồn tại).
     * @param comment Comment cần cập nhật
     * @return Comment đã được cập nhật
     */
    @Override
    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    /**
     * Xóa Comment theo id.
     * @param id id của Comment cần xóa
     */
    @Override
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
}
