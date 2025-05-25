package com.vietwellness.community.service;

import com.vietwellness.community.entity.Post;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức nghiệp vụ liên quan đến Post (Bài đăng).
 */
public interface PostService {

    /**
     * Tạo mới một bài đăng.
     * @param post đối tượng Post cần tạo
     * @return Post đã được tạo
     */
    Post createPost(Post post);

    /**
     * Lấy thông tin bài đăng theo id.
     * @param id id của bài đăng
     * @return Optional chứa Post nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<Post> getPostById(Long id);

    /**
     * Lấy danh sách tất cả bài đăng.
     * @return danh sách Post
     */
    List<Post> getAllPosts();

    /**
     * Cập nhật thông tin bài đăng.
     * @param post đối tượng Post cần cập nhật
     * @return Post đã được cập nhật
     */
    Post updatePost(Post post);

    /**
     * Xóa bài đăng theo id.
     * @param id id của bài đăng cần xóa
     */
    void deletePost(Long id);
}
