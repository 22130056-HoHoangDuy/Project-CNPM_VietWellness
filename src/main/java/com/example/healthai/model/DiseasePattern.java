package com.example.healthai.model;

public class DiseasePattern {
    public final double riskWeight;
    public final String treatment;
    public static final DiseasePattern DEFAULT = new DiseasePattern(0.1, "Theo dõi thêm");

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