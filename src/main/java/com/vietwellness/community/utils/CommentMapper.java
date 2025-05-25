package com.vietwellness.community.utils;

import com.vietwellness.community.dto.CommentDto;
import com.vietwellness.community.entity.Comment;

/**
 * Lớp tiện ích để chuyển đổi giữa Comment entity và CommentDto.
 */
public class CommentMapper {

    /**
     * Chuyển đổi từ Comment entity sang CommentDto.
     * @param comment đối tượng Comment entity
     * @return CommentDto tương ứng
     */
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

    /**
     * Chuyển đổi từ CommentDto sang Comment entity.
     * @param dto đối tượng CommentDto
     * @return Comment entity tương ứng
     */
    public static Comment toEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setContent(dto.getContent());
        comment.setCommentedAt(dto.getCommentedAt());
        return comment;
    }
}
