package com.vietwellness.community.repository;

import com.vietwellness.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
