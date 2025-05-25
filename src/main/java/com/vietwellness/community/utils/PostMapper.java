package com.vietwellness.community.utils;

import com.vietwellness.community.dto.PostDto;
import com.vietwellness.community.entity.Post;

public class PostMapper {

    /**
     * Chuyển đổi từ Entity Post sang DTO PostDto
     * @param post đối tượng Post Entity
     * @return đối tượng PostDto tương ứng
     */
    public static PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setMediaUrl(post.getMediaUrl());
        dto.setAiStatus(post.getStatus()); // trạng thái AI ví dụ: "APPROVED", "NEEDS_VERIFICATION"

        if (post.getAuthor() != null)
            dto.setUserId(post.getAuthor().getId()); // chuyển thông tin tác giả sang userId trong DTO

        if (post.getGroup() != null)
            dto.setGroupId(post.getGroup().getId()); // chuyển thông tin nhóm sang groupId trong DTO

        return dto;
    }

    /**
     * Chuyển đổi từ DTO PostDto sang Entity Post
     * @param dto đối tượng PostDto
     * @return đối tượng Post Entity tương ứng
     */
    public static Post toEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setContent(dto.getContent());
        post.setMediaUrl(dto.getMediaUrl());
        post.setStatus(dto.getAiStatus()); // lấy trạng thái AI từ DTO để set vào entity
        return post;
    }
}
