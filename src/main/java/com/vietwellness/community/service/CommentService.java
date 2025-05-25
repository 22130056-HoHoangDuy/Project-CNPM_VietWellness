// CommentService.java
package com.vietwellness.community.service;

import com.vietwellness.community.entity.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAllComments();
    Comment updateComment(Comment comment);
    void deleteComment(Long id);
}
