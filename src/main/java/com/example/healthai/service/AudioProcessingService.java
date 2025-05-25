package com.example.healthai.service;

import org.springframework.stereotype.Service;

@Service
public class AudioProcessingService {
    // TODO: Integrate real speech-to-text API (e.g., Google Cloud Speech-to-Text)
    public String processAudio(byte[] audioData) {
        System.out.println("Simulating audio processing");
        return "đau ngực dữ dội kèm khó thở"; // Simulated speech recognition
    }
}