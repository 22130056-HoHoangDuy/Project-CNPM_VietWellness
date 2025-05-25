package com.mobile.pomodoro.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "patient_name")
    private String patientName;
    
    @Column(name = "patient_phone")
    private String patientPhone;
}
