package com.vietwellness.community.dto;

import lombok.Data;

@Data
public class GroupDto {
    private Long id;
    private String name;
    private String description;
    private boolean isPrivate;
}
