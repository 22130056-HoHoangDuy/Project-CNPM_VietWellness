package com.vietwellness.community.service;

import com.vietwellness.community.entity.Comment;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức xử lý nghiệp vụ liên quan đến Comment.
 */
public interface CommentService {

    /**
     * Tạo mới một comment.
     * @param comment đối tượng Comment cần tạo
     * @return Comment đã được tạo
     */
    Comment createComment(Comment comment);

    /**
     * Lấy comment theo id.
     * @param id id của comment
     * @return Optional chứa Comment nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<Comment> getCommentById(Long id);

    /**
     * Lấy danh sách tất cả comment.
     * @return danh sách Comment
     */
    List<Comment> getAllComments();

    /**
     * Cập nhật comment.
     * @param comment comment cần cập nhật
     * @return comment đã được cập nhật
     */
    Comment updateComment(Comment comment);

    /**
     * Xóa comment theo id.
     * @param id id của comment cần xóa
     */
    void deleteComment(Long id);
}
