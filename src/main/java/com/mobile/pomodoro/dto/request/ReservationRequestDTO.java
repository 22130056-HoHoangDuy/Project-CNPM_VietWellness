package com.mobile.pomodoro.dto.request;

import com.mobile.pomodoro.entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for reservation requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private Doctor doctor;
    private LocalDateTime date;
    private String patientName;
    private String patientPhone;
}