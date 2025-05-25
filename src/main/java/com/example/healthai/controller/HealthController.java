package com.example.healthai.controller;

import com.example.healthai.model.DiagnosisResult;
import com.example.healthai.model.EmergencyProtocol;
import com.example.healthai.model.UserProfile;
import com.example.healthai.service.AudioProcessingService;
import com.example.healthai.service.HealthAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;


@Controller
public class HealthController {
    @Autowired
    private HealthAnalysisService analysisService;
    @Autowired
    private AudioProcessingService audioService;

    //Luồng chính: Hiển thị thông báo pháp lý khi người dùng truy cập mục "Tư vấn AI"
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("legalNotice", "Kết quả chỉ mang tính tham khảo, không thay thế chẩn đoán từ bác sĩ");
        return "index";
    }
    //Luồng chính: Nhận triệu chứng văn bản, kiểm tra mơ hồ, phân tích, xử lý rủi ro cao, lưu trữ và trả kết quả
    @PostMapping("/analyze")
    public String analyze(@RequestParam String symptoms, Model model) {
        System.out.println("Analyzing symptoms: " + symptoms);
        try {
            // Luồng thay thế: Kiểm tra triệu chứng mơ hồ
            if (analysisService.isVague(symptoms)) {
                model.addAttribute("originalSymptoms", symptoms);
                return "clarification";
            }
            //Luồng chính: Phân tích triệu chứng (so sánh với DDx, PubMed, lịch sử sức khỏe trong HealthAnalysisService)
            DiagnosisResult result = analysisService.analyzeSymptoms(symptoms);
            // Luồng chính:Lưu kết quả mã hóa AES256 và ghi nhật ký kiểm tra
            analysisService.saveEncryptedRecord(result);
            // Luồng chính: Xử lý trường hợp rủi ro cao (>=85%)
            handleEmergencyIfNeeded(result, model);
            //Luồng chính: Trả kết quả chẩn đoán và khuyến nghị
            model.addAttribute("result", result);
            model.addAttribute("topMatches", result.getTopMatches(3));
            return "result";
        } catch (Exception ex) {
            System.out.println("Error during analysis: " + ex.getMessage());
            model.addAttribute("error", "Lỗi phân tích: " + ex.getMessage());
            return "error";
        }
    }
    //Luồng thay thế: Nhận câu trả lời từ bảng câu hỏi, kết hợp với triệu chứng ban đầu, và tiếp tục luồng chính
    @PostMapping("/clarify")
    public String clarify(@RequestParam String originalSymptoms,
                          @RequestParam String symptomAnswer,
                          @RequestParam String painLevelAnswer,
                          @RequestParam String symptomsAnswer,
                          Model model) {
        System.out.println("Clarification received for symptoms: " + originalSymptoms);
        String combinedSymptoms = String.join(" ", originalSymptoms, symptomAnswer, painLevelAnswer, symptomsAnswer);
        try {
            DiagnosisResult result = analysisService.analyzeSymptoms(combinedSymptoms);
            analysisService.saveEncryptedRecord(result);
            handleEmergencyIfNeeded(result, model);
            model.addAttribute("result", result);
            model.addAttribute("topMatches", result.getTopMatches(3));
            return "result";
        } catch (Exception ex) {
            System.out.println("Error during clarification analysis: " + ex.getMessage());
            model.addAttribute("error", "Lỗi phân tích sau làm rõ: " + ex.getMessage());
            return "error";
        }
    }

    // Luồng chính: Bước 2 - Nhận dữ liệu âm thanh, chuyển đổi thành văn bản
    @PostMapping("/process-audio")
    public String processAudio(@RequestParam("audioData") String audioData, Model model) {
        System.out.println("Processing audio data");
        try {
            byte[] audioBytes = Base64.getDecoder().decode(audioData);
            String symptoms = audioService.processAudio(audioBytes);
            System.out.println("Symptoms from audio: " + symptoms);
            model.addAttribute("symptoms", symptoms);
            model.addAttribute("legalNotice", "Kết quả chỉ mang tính tham khảo, không thay thế chẩn đoán từ bác sĩ");
            return "index";
        } catch (Exception ex) {
            System.out.println("Error processing audio: " + ex.getMessage());
            model.addAttribute("error", "Lỗi xử lý âm thanh: " + ex.getMessage());
            return "error";
        }
    }
    // Luồng chính: Kích hoạt giao thức khẩn cấp nếu rủi ro cao (>=85%)
    private void handleEmergencyIfNeeded(DiagnosisResult result, Model model) {
        if (result.getRiskScore() >= 85.0) {
            // TODO: Actually call 115, share GPS coordinates, and notify emergency contacts
            // This would require integration with telephony APIs, location services, and notification systems
            EmergencyProtocol.activate(
                    result.getMainSymptoms(),
                    "GPS Coordinates", // TODO: Get real GPS coordinates
                    UserProfile.getEmergencyContacts()
            );
            model.addAttribute("emergency", "CẢNH BÁO NGUY HIỂM! Đã kích hoạt dịch vụ cấp cứu.");
        }
    }
}