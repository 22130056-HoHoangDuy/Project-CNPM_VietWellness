package com.vietwellness.community.utils;

import com.vietwellness.community.dto.CommentDto;
import com.vietwellness.community.entity.Comment;

public class CommentMapper {
    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCommentedAt(comment.getCommentedAt());

        if (comment.getCommenter() != null)
            dto.setUserId(comment.getCommenter().getId());

        if (comment.getPost() != null)
            dto.setPostId(comment.getPost().getId());

        return dto;
    }

    public static Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setContent(dto.getContent());
        comment.setCommentedAt(dto.getCommentedAt());
        return comment;
    }
}
