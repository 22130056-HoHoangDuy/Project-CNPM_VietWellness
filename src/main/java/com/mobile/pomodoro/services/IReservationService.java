package com.mobile.pomodoro.services;

import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService extends IInitializerData {
    
    List<Doctor> getDoctorsList();

    List<Schedule> getDoctorSchedule(Doctor doctor, LocalDateTime date);

    MessageResponseDTO saveReservation(Doctor doctor, LocalDateTime date, String patientName, String patientPhone);
}
