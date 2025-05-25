package com.example.healthai.model;

import java.util.List;

public class UserProfile {
    // Luồng chính: Lấy danh sách liên lạc khẩn cấp
    public static List<String> getEmergencyContacts() {
        return List.of("0123456789", "nguoinha@example.com");
    }
}