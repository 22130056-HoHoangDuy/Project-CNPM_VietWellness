package com.example.healthai.model;

import java.util.List;

public class EmergencyProtocol {
    // Luồng chính: Kích hoạt giao thức khẩn cấp cho trường hợp rủi ro cao
    public static void activate(String symptoms, String location, List<String> contacts) {
        System.out.printf("Đã kích hoạt cấp cứu:\nTriệu chứng: %s\nVị trí: %s\nLiên lạc: %s%n",
                symptoms, location, contacts);
    }
}