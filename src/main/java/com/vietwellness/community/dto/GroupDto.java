package com.vietwellness.community.dto;

import lombok.Data;

/**
 * DTO này giúp đơn giản hóa việc hiển thị và thao tác với thông tin nhóm.
 */
@Data
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    // Cho biết nhóm có ở chế độ riêng tư hay không. true = nhóm riêng tư, false = nhóm công khai.
    private boolean isPrivate;
}
