// PostService.java
package com.vietwellness.community.service;

import com.vietwellness.community.entity.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    Post updatePost(Post post);
    void deletePost(Long id);
}
