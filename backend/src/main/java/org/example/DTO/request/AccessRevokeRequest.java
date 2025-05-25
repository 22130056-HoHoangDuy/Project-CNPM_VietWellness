package org.example.DTO.request;

import lombok.Data;

@Data
public class AccessRevokeRequest {
    private String username;
    private String granteeName; // ví dụ: "Bác sĩ Nguyễn Văn A"
}

