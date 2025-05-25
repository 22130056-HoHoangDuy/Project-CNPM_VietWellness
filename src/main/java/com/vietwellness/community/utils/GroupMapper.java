package com.vietwellness.community.utils;

import com.vietwellness.community.dto.GroupDto;
import com.vietwellness.community.entity.Group;

public class GroupMapper {
    public static GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setPrivate(group.isPrivate());
        return dto;
    }

    public static Group toEntity(GroupDto dto) {
        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setPrivate(dto.isPrivate());
        return group;
    }
}
