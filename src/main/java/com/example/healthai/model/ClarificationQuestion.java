package com.example.healthai.model;

public enum ClarificationQuestion {
    SYMPTOM("Triệu chứng biểu hiện?"),
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