package com.vietwellness.community.utils;

import com.vietwellness.community.dto.GroupMembershipDto;
import com.vietwellness.community.entity.GroupMembership;

public class GroupMembershipMapper {

    /**
     * Chuyển đổi từ Entity GroupMembership sang DTO GroupMembershipDto
     * @param membership đối tượng GroupMembership Entity
     * @return đối tượng GroupMembershipDto tương ứng
     */
    public static GroupMembershipDto toDto(GroupMembership membership) {
        GroupMembershipDto dto = new GroupMembershipDto();
        dto.setId(membership.getId());
        dto.setApproved(membership.isApproved());

        if (membership.getMember() != null)
            dto.setUserId(membership.getMember().getId());

        if (membership.getGroup() != null)
            dto.setGroupId(membership.getGroup().getId());

        return dto;
    }

    /**
     * Chuyển đổi từ DTO GroupMembershipDto sang Entity GroupMembership
     * @param dto đối tượng GroupMembershipDto
     * @return đối tượng GroupMembership Entity tương ứng
     */
    public static GroupMembership toEntity(GroupMembershipDto dto) {
        GroupMembership membership = new GroupMembership();
        membership.setId(dto.getId());
        membership.setApproved(dto.isApproved());
        return membership;
    }
}
