package com.vietwellness.community.utils;

import com.vietwellness.community.dto.PostDto;
import com.vietwellness.community.entity.Post;

public class PostMapper {
    public static PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setMediaUrl(post.getMediaUrl());
        dto.setAiStatus(post.getStatus());

        if (post.getAuthor() != null)
            dto.setUserId(post.getAuthor().getId());

        if (post.getGroup() != null)
            dto.setGroupId(post.getGroup().getId());

        return dto;
    }

    public static Post toEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setContent(dto.getContent());
        post.setMediaUrl(dto.getMediaUrl());
        post.setStatus(dto.getAiStatus());
        return post;
    }
}
