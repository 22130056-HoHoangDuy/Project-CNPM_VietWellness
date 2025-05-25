package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Post;
import com.vietwellness.community.repository.PostRepository;
import com.vietwellness.community.service.PostService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của PostService.
 * Xử lý nghiệp vụ liên quan đến bài đăng (Post).
 */
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    /**
     * Constructor để inject PostRepository.
     * @param postRepository repository thao tác với Post
     */
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    /**
     * Tạo bài đăng mới.
     * @param post đối tượng Post mới cần lưu
     * @return Post đã được lưu
     */
    @Override
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    /**
     * Lấy bài đăng theo id.
     * @param id id của bài đăng
     * @return Optional chứa Post nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả bài đăng.
     * @return danh sách Post
     */
    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    /**
     * Cập nhật bài đăng.
     * @param post Post cần cập nhật
     * @return Post đã được cập nhật
     */
    @Override
    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    /**
     * Xóa bài đăng theo id.
     * @param id id của bài đăng cần xóa
     */
    @Override
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
