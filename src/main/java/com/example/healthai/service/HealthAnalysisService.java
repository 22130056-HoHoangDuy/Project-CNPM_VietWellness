package com.example.healthai.service;

import com.example.healthai.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HealthAnalysisService {
    private static final Map<String, DiseasePattern> DISEASE_DB = initDiseaseDB();

    @Autowired
    private EncryptionService encryptionService;

    public DiagnosisResult analyzeSymptoms(String symptoms) {
        System.out.println("Analyzing combined symptoms: " + symptoms);
        List<String> keywords = extractKeywords(symptoms);
        double riskScore = calculateRiskScore(keywords);
        List<DiseaseMatch> matches = findDiseaseMatches(keywords);
        String recommendation = generateRecommendation(matches);

        return new DiagnosisResult(symptoms, matches, riskScore, recommendation);
    }

    public boolean isVague(String symptoms) {
        return symptoms.trim().split("\\s+").length < 5
                || symptoms.matches("(?i).*\\b(mệt|khó chịu|không ổn)\\b.*");
    }

    public void saveEncryptedRecord(DiagnosisResult result) {
        try {
            String data = String.format("%s|%.2f|%s", result.getMainSymptoms(), result.getRiskScore(), result.getRecommendation());
            String encrypted = encryptionService.encrypt(data);
            System.out.println("Đã lưu bản ghi mã hóa: " + encrypted);
            logAuditEvent(result);
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lưu trữ hồ sơ", ex);
        }
    }

    private double calculateRiskScore(List<String> keywords) {
        // TODO: Use a trained logistic regression model for accurate risk scoring
        double score = keywords.stream()
                .mapToDouble(k -> DISEASE_DB.getOrDefault(k, DiseasePattern.DEFAULT).getRiskWeight())
                .sum();
        return 1 / (1 + Math.exp(-score)) * 100;
    }

    private void logAuditEvent(DiagnosisResult result) {
        String logEntry = String.format(
                "MED_AI_CONSULT|%s|%.2f|%s",
                new Date(),
                result.getRiskScore(),
                result.getMainSymptoms().hashCode()
        );
        System.out.println("[Audit Log] " + logEntry);
    }

    private static List<String> extractKeywords(String text) {
        String lower = text.toLowerCase();
        return DISEASE_DB.keySet().stream()
                .filter(key -> lower.contains(key))
                .collect(Collectors.toList());
    }

    private static List<DiseaseMatch> findDiseaseMatches(List<String> keywords) {
        // TODO: Integrate with large DDx database (500,000+ cases) and PubMed guidelines
        // TODO: Incorporate personal health history if available
        return keywords.stream()
                .map(k -> {
                    DiseasePattern pattern = DISEASE_DB.getOrDefault(k, DiseasePattern.DEFAULT);
                    return new DiseaseMatch(k, pattern.getRiskWeight(), pattern.getTreatment());
                })
                .collect(Collectors.toList());
    }

    private static String generateRecommendation(List<DiseaseMatch> matches) {
        return matches.stream()
                .sorted(Comparator.comparingDouble(DiseaseMatch::getScore).reversed())
                .map(m -> "- " + m.getDisease() + ": " + m.getTreatment())
                .collect(Collectors.joining("\n"));
    }

    private static Map<String, DiseasePattern> initDiseaseDB() {
        // TODO: Expand with comprehensive DDx database
        return Map.ofEntries(
                // ===== Cấp cứu & tim mạch =====
                Map.entry("mất ý thức",               new DiseasePattern(0.99, "Gọi cấp cứu ngay")),
                Map.entry("đau ngực dữ dội",          new DiseasePattern(0.95, "Gọi cấp cứu ngay (115)")),
                Map.entry("hồi hộp tim nhanh",        new DiseasePattern(0.90, "Gọi cấp cứu hoặc đến viện ngay")),
                Map.entry("tức ngực",                 new DiseasePattern(0.88, "Thăm khám tim mạch khẩn cấp")),
                Map.entry("phù chi dưới",             new DiseasePattern(0.80, "Siêu âm tim, kiểm tra suy tim")),

                // ===== Hô hấp =====
                Map.entry("khó thở nặng",             new DiseasePattern(0.95, "Gọi cấp cứu ngay")),
                Map.entry("khó thở",                  new DiseasePattern(0.85, "Thở oxy, đến viện ngay")),
                Map.entry("ho khan",                  new DiseasePattern(0.40, "Uống siro ho, nghỉ ngơi")),
                Map.entry("ho có đờm",                new DiseasePattern(0.50, "Thuốc long đờm, theo dõi 1 tuần")),
                Map.entry("khạc ra máu",              new DiseasePattern(0.90, "Khám khoa hô hấp ngay")),
                Map.entry("viêm họng",                new DiseasePattern(0.50, "Súc nước muối, kháng sinh nếu cần")),
                Map.entry("đau rát họng",             new DiseasePattern(0.45, "Uống nước ấm, súc muối")),

                // ===== Tiêu hóa =====
                Map.entry("tiêu chảy cấp",            new DiseasePattern(0.65, "Bù nước điện giải, khám nếu >3 ngày")),
                Map.entry("tiêu chảy mãn",            new DiseasePattern(0.55, "Xét nghiệm phân, khám tiêu hóa")),
                Map.entry("nôn mửa liên tục",         new DiseasePattern(0.80, "Bù nước oresol, khám ngay nếu >6h")),
                Map.entry("ợ nóng",                   new DiseasePattern(0.30, "Thuốc kháng acid, tránh cay nóng")),
                Map.entry("buồn nôn",                   new DiseasePattern(0.30, "Uống nước, nghỉ ngơi, hít thở sâu")),
                Map.entry("đau thượng vị",             new DiseasePattern(0.60, "Thuốc giảm tiết acid, khám tiêu hóa")),
                Map.entry("nấc cụt kéo dài",          new DiseasePattern(0.40, "Uống nước, khám nếu >48h")),

                // ===== Thận – tiết niệu =====
                Map.entry("tiểu buốt",                new DiseasePattern(0.55, "Uống nhiều nước, khám nếu kéo dài")),
                Map.entry("tiểu ra máu",              new DiseasePattern(0.80, "Xét nghiệm nước tiểu, khám ngay")),
                Map.entry("đau hông lưng",            new DiseasePattern(0.60, "Siêu âm thận niệu, khám sớm")),

                // ===== Thần kinh =====
                Map.entry("chóng mặt",                new DiseasePattern(0.45, "Nghỉ ngơi, đo huyết áp")),
                Map.entry("đau đầu",                  new DiseasePattern(0.50, "Thuốc giảm đau, nếu nặng khám ngay")),
                Map.entry("run tay chân",             new DiseasePattern(0.55, "Khám thần kinh, kiểm tra Parkinson?")),
                Map.entry("cứng cổ",                  new DiseasePattern(0.65, "Khẩn cấp kiểm tra dấu hiệu màng não")),
                Map.entry("tê bì chân tay",           new DiseasePattern(0.60, "Khám thần kinh, đo điện cơ")),

                // ===== Nội tiết =====
                Map.entry("sốt cao",                  new DiseasePattern(0.60, "Hạ sốt, khám nếu ≥39°C")),
                Map.entry("đổ mồ hôi trộm",           new DiseasePattern(0.40, "Kiểm tra nội tiết, khám ngoại trú")),
                Map.entry("sụt cân nhanh",            new DiseasePattern(0.65, "Xét nghiệm máu tổng quát")),

                // ===== Cơ – xương – khớp =====
                Map.entry("đau lưng",                 new DiseasePattern(0.35, "Chườm nóng, vật lý trị liệu")),
                Map.entry("đau khớp",                 new DiseasePattern(0.40, "Thuốc kháng viêm, nghỉ ngơi")),
                Map.entry("đau vai",                  new DiseasePattern(0.45, "Tập phục hồi chức năng, khám chuyên khoa")),

                // ===== Da liễu =====
                Map.entry("phát ban da",              new DiseasePattern(0.45, "Thuốc bôi kháng viêm, theo dõi")),
                Map.entry("mụn trứng cá nặng",        new DiseasePattern(0.50, "Thuốc bôi hoặc uống, khám da liễu")),
                Map.entry("ngứa toàn thân",           new DiseasePattern(0.50, "Thuốc kháng histamine, khám da liễu")),

                // ===== Mắt – tai mũi họng =====
                Map.entry("đau mắt đỏ",               new DiseasePattern(0.55, "Nước mắt nhân tạo, khám chuyên khoa")),
                Map.entry("mờ mắt đột ngột",          new DiseasePattern(0.85, "Khẩn cấp khám nhãn khoa")),
                Map.entry("đau tai",                  new DiseasePattern(0.50, "Kháng sinh nhỏ tai, khám nếu nặng")),
                Map.entry("nghẹt mũi",                new DiseasePattern(0.30, "Xịt muối sinh lý, nghỉ ngơi")),

                // ===== Phụ khoa – nam khoa =====
                Map.entry("đau bụng dưới",            new DiseasePattern(0.60, "Siêu âm phụ khoa, khám ngay")),
                Map.entry("chảy máu âm đạo bất thường", new DiseasePattern(0.80, "Khám phụ khoa gấp")),
                Map.entry("tiểu khó",                 new DiseasePattern(0.50, "Khám niệu đạo, xét nghiệm nước tiểu")),

                // ===== Sức khỏe tinh thần =====
                Map.entry("lo âu nặng",               new DiseasePattern(0.50, "Tư vấn tâm lý, theo dõi")),
                Map.entry("trầm cảm",                 new DiseasePattern(0.55, "Liên hệ chuyên gia tâm thần")),
                Map.entry("mất ngủ kinh niên",        new DiseasePattern(0.45, "Tư vấn giấc ngủ, khám chuyên khoa")),

                // ===== Các triệu chứng khác =====
                Map.entry("mệt mỏi",                  new DiseasePattern(0.40, "Nghỉ ngơi, uống nhiều nước")),
                Map.entry("chán ăn",                  new DiseasePattern(0.45, "Xét nghiệm dinh dưỡng, khám nội")),
                Map.entry("khó thở khi gắng sức",     new DiseasePattern(0.70, "Khám tim mạch, test gắng sức")),
                Map.entry("dữ dội",                  new DiseasePattern(0.60, "Theo dõi thêm nếu triệu chứng khác thường")),
                Map.entry("rất",                  new DiseasePattern(0.50, "Theo dõi thêm nếu triệu chứng khác thường")),
                Map.entry("khá",                  new DiseasePattern(0.25, "Theo dõi thêm nếu triệu chứng khác thường")),
                Map.entry("nhẹ",                  new DiseasePattern(0.15, "Theo dõi thêm nếu triệu chứng khác thường")),
                Map.entry("hơi",                  new DiseasePattern(0.10, "Theo dõi thêm nếu triệu chứng khác thường"))
        );
    }
}