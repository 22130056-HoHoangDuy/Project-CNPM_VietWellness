package com.example.healthai.model;

public class DiseasePattern {
    public final double riskWeight;
    public final String treatment;
    // Luồng chính: Mẫu mặc định cho các triệu chứng không khớp
    public static final DiseasePattern DEFAULT = new DiseasePattern(0.1, "Theo dõi thêm");

    // Luồng chính: Lưu trữ trọng số rủi ro và phương pháp điều trị cho mỗi bệnh
    public DiseasePattern(double riskWeight, String treatment) {
        this.riskWeight = riskWeight;
        this.treatment = treatment;
    }

    public double getRiskWeight() {
        return riskWeight;
    }

    public String getTreatment() {
        return treatment;
    }
}