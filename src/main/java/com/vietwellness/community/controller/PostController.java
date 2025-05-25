package com.vietwellness.community.controller;

import com.vietwellness.community.entity.Post;
import com.vietwellness.community.service.PostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các yêu cầu liên quan đến bài viết (Post).
 *
 * URL gốc: /api/posts
 *
 * Bao gồm các chức năng:
 * - Tạo bài viết
 * - Lấy bài viết theo ID
 * - Lấy danh sách tất cả bài viết
 * - Cập nhật bài viết
 * - Xoá bài viết
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * Constructor để inject PostService vào controller.
     *
     * @param postService Dịch vụ xử lý logic liên quan đến bài viết
     */
    public PostController(PostService postService){
        this.postService = postService;
    }

    /**
     * API tạo mới một bài viết.
     *
     * @param post Đối tượng bài viết nhận từ request body
     * @return Trả về bài viết đã được tạo thành công
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post created = postService.createPost(post);
        return ResponseEntity.ok(created);
    }

    /**
     * API lấy một bài viết theo ID.
     *
     * @param id ID của bài viết cần lấy
     * @return Trả về bài viết nếu tìm thấy, ngược lại trả về 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả các bài viết.
     *
     * @return Danh sách bài viết trong hệ thống
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * API cập nhật một bài viết theo ID.
     *
     * @param id ID của bài viết cần cập nhật
     * @param post Dữ liệu bài viết mới
     * @return Bài viết sau khi cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post){
        post.setId(id); // Đảm bảo ID đúng khi cập nhật
        Post updated = postService.updatePost(post);
        return ResponseEntity.ok(updated);
    }

    /**
     * API xoá một bài viết theo ID.
     *
     * @param id ID của bài viết cần xoá
     * @return Trả về 204 No Content nếu xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
