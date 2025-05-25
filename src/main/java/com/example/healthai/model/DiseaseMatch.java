package com.example.healthai.model;

public class DiseaseMatch {
    public final String disease;
    public final double score;
    public final String treatment;

    // Luồng chính: Lưu trữ thông tin về bệnh khớp với triệu chứng
    public DiseaseMatch(String disease, double score, String treatment) {
        this.disease = disease;
        this.score = score;
        this.treatment = treatment;
    }

    public String getDisease() {
        return disease;
    }

    public double getScore() {
        return score;
    }

    public String getTreatment() {
        return treatment;
    }
}