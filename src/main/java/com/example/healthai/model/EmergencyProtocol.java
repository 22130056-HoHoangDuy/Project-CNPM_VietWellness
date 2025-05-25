package com.example.healthai.model;

import java.util.List;

public class EmergencyProtocol {
    public static void activate(String symptoms, String location, List<String> contacts) {
        System.out.printf("Đã kích hoạt cấp cứu:\nTriệu chứng: %s\nVị trí: %s\nLiên lạc: %s%n",
                symptoms, location, contacts);
    }
}