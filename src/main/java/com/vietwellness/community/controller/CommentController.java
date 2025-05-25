package com.vietwellness.community.controller;

import com.vietwellness.community.entity.Comment;
import com.vietwellness.community.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến comment (bình luận).
 *
 * URL cơ bản: /api/comments
 *
 * Các chức năng:
 * - Tạo mới comment
 * - Lấy comment theo ID
 * - Lấy tất cả comment
 * - Cập nhật comment
 * - Xoá comment
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructor để inject CommentService
     *
     * @param commentService Dịch vụ xử lý logic liên quan đến comment
     */
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    /**
     * API tạo mới một comment.
     *
     * @param comment Đối tượng comment gửi từ client
     * @return Comment sau khi được tạo thành công
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        Comment created = commentService.createComment(comment);
        return ResponseEntity.ok(created);
    }

    /**
     * API lấy chi tiết một comment theo ID.
     *
     * @param id ID của comment cần lấy
     * @return Comment nếu tìm thấy, nếu không trả về 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả comment.
     *
     * @return Danh sách tất cả comment
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentService.getAllComments());
    }

    /**
     * API cập nhật một comment theo ID.
     *
     * @param id ID của comment cần cập nhật
     * @param comment Dữ liệu comment mới từ client
     * @return Comment sau khi cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment){
        comment.setId(id); // Gán lại ID từ path vào đối tượng comment
        Comment updated = commentService.updateComment(comment);
        return ResponseEntity.ok(updated);
    }

    /**
     * API xoá một comment theo ID.
     *
     * @param id ID của comment cần xoá
     * @return Trạng thái thành công (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
