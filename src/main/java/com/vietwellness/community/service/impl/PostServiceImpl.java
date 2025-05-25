// PostServiceImpl.java
package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Post;
import com.vietwellness.community.repository.PostRepository;
import com.vietwellness.community.service.PostService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }
}
