package com.vietwellness.community.repository;

import com.vietwellness.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}

