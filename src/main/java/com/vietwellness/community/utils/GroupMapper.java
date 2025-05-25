package com.vietwellness.community.utils;

import com.vietwellness.community.dto.GroupDto;
import com.vietwellness.community.entity.Group;

public class GroupMapper {

    /**
     * Chuyển đổi từ Entity Group sang DTO GroupDto
     * @param group đối tượng Group Entity
     * @return đối tượng GroupDto tương ứng
     */
    public static GroupDto toDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setPrivate(group.isPrivate());
        return dto;
    }

    /**
     * Chuyển đổi từ DTO GroupDto sang Entity Group
     * @param dto đối tượng GroupDto
     * @return đối tượng Group Entity tương ứng
     */
    public static Group toEntity(GroupDto dto) {
        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setPrivate(dto.isPrivate());
        return group;
    }
}
