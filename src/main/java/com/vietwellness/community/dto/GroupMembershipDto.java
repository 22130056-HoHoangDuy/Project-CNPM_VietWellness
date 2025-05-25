package com.vietwellness.community.dto;

import lombok.Data;

/**
 * Chứa các thuộc tính cơ bản dùng để truyền dữ liệu giữa client và server.
 */
@Data
public class GroupMembershipDto {
    private Long id;
    private Long userId;
    private Long groupId;
    private String role;
    private boolean approved; // Trạng thái phê duyệt thành viên (true nếu đã được duyệt)
}
