package com.mobile.pomodoro.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Entity class representing a Medical Facility in the system.
 */
@Entity
@Table(name = "medical_facilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalFacility {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;
    
    @OneToMany(mappedBy = "medicalFacility", cascade = CascadeType.ALL)
    private List<Doctor> doctors;
}