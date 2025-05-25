package com.vietwellness.community.utils;

import com.vietwellness.community.dto.GroupMembershipDto;
import com.vietwellness.community.entity.GroupMembership;

public class GroupMembershipMapper {
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

    public static GroupMembership toEntity(GroupMembershipDto dto) {
        GroupMembership membership = new GroupMembership();
        membership.setId(dto.getId());
        membership.setApproved(dto.isApproved());
        return membership;
    }
}
