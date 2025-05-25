package com.example.healthai.model;

import java.util.List;
import java.util.stream.Collectors;

public class DiagnosisResult {
    private final String mainSymptoms;
    private final List<DiseaseMatch> matches;
    private final double riskScore;
    private final String recommendation;

    public DiagnosisResult(String symptoms, List<DiseaseMatch> matches, double risk, String advice) {
        this.mainSymptoms = symptoms;
        this.matches = matches;
        this.riskScore = risk;
        this.recommendation = advice;
    }

    public String getTopMatches(int n) {
        return matches.stream()
                .sorted((m1, m2) -> Double.compare(m2.getScore(), m1.getScore()))
                .limit(n)
                .map(m -> m.getDisease() + " (" + String.format("%.1f%%", m.getScore() * 100) + ")")
                .collect(Collectors.joining("\n- "));
    }

    public String getMainSymptoms() { return mainSymptoms; }
    public double getRiskScore() { return riskScore; }
    public String getRecommendation() { return recommendation; }
    public List<DiseaseMatch> getMatches() { return matches; }
}