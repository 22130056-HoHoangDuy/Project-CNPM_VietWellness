import javax.crypto.*;
import javax.crypto.spec.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AdvancedHealthAI extends JFrame {
    // Cấu hình mã hóa
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final SecretKey AES_KEY = generateAESKey();

    // Cấu hình audio
    private static final AudioFormat AUDIO_FORMAT = new AudioFormat(16000, 16, 1, true, true);

    // Cơ sở dữ liệu triệu chứng
    private static final Map<String, DiseasePattern> DISEASE_DB = initDiseaseDB();

    // Các thành phần GUI
    private JTextArea inputArea = new JTextArea(5, 40);
    private JTextArea resultArea = new JTextArea(15, 60);
    private JButton recordButton = new JButton("Ghi âm");
    private JButton analyzeButton = new JButton("Phân tích");
    private volatile boolean isRecording = false;

    public AdvancedHealthAI() {
        super("AI Tư Vấn Sức Khỏe");
        setupGUI();
        setupEventHandlers();
    }

    private void setupGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Triệu chứng:"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        // Panel nút bấm
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(recordButton);
        buttonPanel.add(analyzeButton);
        inputPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel kết quả
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.add(new JLabel("Kết quả:"), BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Thêm các thành phần chính
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        pack();
        setSize(800, 600);
    }

    private void setupEventHandlers() {
        recordButton.addActionListener(e -> toggleRecording());
        analyzeButton.addActionListener(e -> new AnalysisWorker().execute());
    }
// Xử lí ghi âm
    private void toggleRecording() {
        isRecording = !isRecording;
        recordButton.setText(isRecording ? "Dừng ghi" : "Ghi âm");

        if (isRecording) {
            new Thread(this::recordAudio).start();
        }
    }

    private void recordAudio() {
        try (TargetDataLine line = AudioSystem.getTargetDataLine(AUDIO_FORMAT)) {
            line.open(AUDIO_FORMAT);
            line.start();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];

            while (isRecording) {
                int count = line.read(buffer, 0, buffer.length);
                if (count > 0) out.write(buffer, 0, count);
            }

            processAudio(out.toByteArray());
        } catch (Exception ex) {
            showError("Lỗi ghi âm: " + ex.getMessage());
        }
    }

    private void processAudio(byte[] audioData) {
        new SwingWorker<String, Void>() {
            protected String doInBackground() {
                return "đau ngực dữ dội kèm khó thở"; // Giả lập nhận dạng giọng nói
            }

            protected void done() {
                try {
                    inputArea.setText(get());
                } catch (Exception ex) {
                    showError("Lỗi xử lý giọng nói");
                }
            }
        }.execute();
    }
// Phân tích triệu chứng
    class AnalysisWorker extends SwingWorker<DiagnosisResult, Void> {
        protected DiagnosisResult doInBackground() {
            String symptoms = inputArea.getText();

            if (isVague(symptoms)) {
                String clarification = getClarification();
                symptoms += " " + clarification;
            }

            return analyzeSymptoms(symptoms);
        }

        protected void done() {
            try {
                DiagnosisResult result = get();
                displayResult(result);
                handleEmergencyIfNeeded(result);
                saveEncryptedRecord(result);
            } catch (Exception ex) {
                showError("Lỗi phân tích: " + ex.getMessage());
            }
        }
    }
    // Phân tích các triệu chứng và đưa ra chẩn đoán
    private DiagnosisResult analyzeSymptoms(String symptoms) {
        List<String> keywords = extractKeywords(symptoms);
        double riskScore = calculateRiskScore(keywords);

        List<DiseaseMatch> matches = findDiseaseMatches(keywords);
        String recommendation = generateRecommendation(matches);

        return new DiagnosisResult(
                symptoms,
                matches,
                riskScore,
                recommendation
        );
    }
// Tính điểm rủi ro sử dụng hàm logistic
    private double calculateRiskScore(List<String> keywords) {
        double score = keywords.stream()
                .mapToDouble(k -> DISEASE_DB.getOrDefault(k, DiseasePattern.DEFAULT).riskWeight)
                .sum();

        return 1 / (1 + Math.exp(-score)) * 100;
    }
// Kiểm tra tính mơ hồ của triệu chứng
    private boolean isVague(String symptoms) {
        return symptoms.split("\\s+").length < 5
                || symptoms.matches("(?i).*\\b(mệt|khó chịu|không ổn)\\b.*");
    }
// Thu thập thông tin làm rõ từ người dùng
    private String getClarification() {
        List<String> answers = new ArrayList<>();
        for (ClarificationQuestion question : ClarificationQuestion.values()) {
            String answer = JOptionPane.showInputDialog(question.getQuestion());
            if (answer == null) answer = "không biết";
            answers.add(answer);
        }
        return String.join(" ", answers);
    }
// Lưu trữ kết quả đã mã hóa
    private void saveEncryptedRecord(DiagnosisResult result) {
        try {
            String record = result.toEncryptedString();
            System.out.println("Đã lưu bản ghi mã hóa: " + record);
            logAuditEvent(result);
        } catch (Exception ex) {
            showError("Lỗi lưu trữ hồ sơ");
        }
    }
//Ghi log kiểm tra
    private void logAuditEvent(DiagnosisResult result) {
        String logEntry = String.format(
                "MED_AI_CONSULT|%s|%.2f|%s",
                new Date(),
                result.riskScore,
                result.mainSymptoms.hashCode()
        );
        System.out.println("[Audit Log] " + logEntry);
    }

    private void displayResult(DiagnosisResult result) {
        String output = String.format(
                "Chẩn đoán sơ bộ:\n- %s\n\nĐộ rủi ro: %.2f%%\n\nKhuyến nghị:\n%s\n\nLưu ý: Kết quả chỉ mang tính tham khảo, không thay thế chẩn đoán từ bác sĩ.",
                result.getTopMatches(3),
                result.riskScore,
                result.recommendation
        );
        resultArea.setText(output);
    }
// Xử lý trường hợp khẩn cấp nếu cần
    private void handleEmergencyIfNeeded(DiagnosisResult result) {
        if (result.riskScore >= 85.0) {
            EmergencyProtocol.activate(
                    result.mainSymptoms,
                    "GPS Coordinates",
                    UserProfile.getEmergencyContacts()
            );
            showEmergencyAlert(result);
        }
    }

    public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {
                new AdvancedHealthAI().setVisible(true);
            });

    }
    //Hiển thị thông báo lỗi
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
//Hiển thị cảnh báo khẩn cấp
    private void showEmergencyAlert(DiagnosisResult result) {
        JOptionPane.showMessageDialog(this,
                "CẢNH BÁO NGUY HIỂM!\nĐã kích hoạt dịch vụ cấp cứu.\nTriệu chứng: " + result.mainSymptoms,
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
    }
//Lớp chứa kết quả chẩn đoán
    static class DiagnosisResult {
        final String mainSymptoms;
        final List<DiseaseMatch> matches;
        final double riskScore;
        final String recommendation;

        DiagnosisResult(String symptoms, List<DiseaseMatch> matches, double risk, String advice) {
            this.mainSymptoms = symptoms;
            this.matches = matches;
            this.riskScore = risk;
            this.recommendation = advice;
        }
//Lấy top các bệnh khớp nhất
        String getTopMatches(int n) {
            return matches.stream()
                    .sorted(Comparator.comparingDouble(DiseaseMatch::score).reversed())
                    .limit(n)
                    .map(m -> m.disease + " (" + String.format("%.1f%%)", m.score*100))
                    .collect(Collectors.joining("\n- "));
        }
// Chuyển kết quả thành chuỗi đã mã hóa
        String toEncryptedString() throws Exception {
            String data = String.format("%s|%.2f|%s", mainSymptoms, riskScore, recommendation);
            return encrypt(data);
        }
    }
// Bản ghi thông tin khớp bệnh
    record DiseaseMatch(String disease, double score, String treatment) {}
// Mẫu bệnh với trọng số rủi ro và hướng xử lý
    record DiseasePattern(double riskWeight, String treatment) {
        static final DiseasePattern DEFAULT = new DiseasePattern(0.1, "Theo dõi thêm");
    }
    // Danh sách câu hỏi làm rõ triệu chứng
    enum ClarificationQuestion {
        SYMPTOM("Triệu chứng biểu hiện ?"),
        PAIN_LEVEL("Mức độ đau (hơi, nhẹ, khá, rất, dữ dội)?"),
        SYMPTOMS("Có triệu chứng nào đi kèm không?");

        private final String question;

        ClarificationQuestion(String question) {
            this.question = question;
        }

        public String getQuestion() {
            return question;
        }
    }
// Trích xuất từ khóa triệu chứng từ văn bản
    private static List<String> extractKeywords(String text) {
        String lower = text.toLowerCase();
        return DISEASE_DB.keySet().stream()
                .filter(key -> lower.contains(key))
                .collect(Collectors.toList());
    }
// Tìm các bệnh khớp với từ khóa
    private static List<DiseaseMatch> findDiseaseMatches(List<String> keywords) {
        return keywords.stream()
                .map(k -> {
                    DiseasePattern pattern = DISEASE_DB.getOrDefault(k, DiseasePattern.DEFAULT);
                    return new DiseaseMatch(k, pattern.riskWeight, pattern.treatment);
                })
                .collect(Collectors.toList());
    }
// Tạo khuyến nghị từ các kết quả khớp
    private static String generateRecommendation(List<DiseaseMatch> matches) {
        return matches.stream()
                .sorted(Comparator.comparingDouble(DiseaseMatch::score).reversed())
                .map(m -> "- " + m.disease + ": " + m.treatment)
                .collect(Collectors.joining("\n"));
    }

// Mã hóa dữ liệu sử dụng AES-GCM
    private static String encrypt(String text) throws Exception {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, AES_KEY, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        byte[] encrypted = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(cipherText, 0, encrypted, iv.length, cipherText.length);

        return Base64.getEncoder().encodeToString(encrypted);
    }
//  Tạo khóa AES 256-bit
    private static SecretKey generateAESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi tạo khóa AES");
        }
    }
// Khởi tạo cơ sở dữ liệu triệu chứng-bệnh
    private static Map<String, DiseasePattern> initDiseaseDB() {
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

// Lớp xử lý các giao thức khẩn cấp
    static class EmergencyProtocol {
        static void activate(String symptoms, String location, List<String> contacts) {
            System.out.printf("Đã kích hoạt cấp cứu:\nTriệu chứng: %s\nVị trí: %s\nLiên lạc: %s%n",
                    symptoms, location, contacts);
        }
    }

// Lớp lấy thông tin người dùng
    static class UserProfile {
        static List<String> getEmergencyContacts() {
            return List.of("0123456789", "nguoinha@example.com");
        }
    }
}