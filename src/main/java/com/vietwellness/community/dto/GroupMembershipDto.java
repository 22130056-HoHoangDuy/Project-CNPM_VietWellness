package com.vietwellness.community.dto;

import lombok.Data;

@Data
public class GroupMembershipDto {
    private Long id;
    private Long userId;
    private Long groupId;
    private String role;
    private boolean approved;
}
