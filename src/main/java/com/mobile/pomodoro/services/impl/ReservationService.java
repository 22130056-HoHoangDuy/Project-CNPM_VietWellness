package com.mobile.pomodoro.services.impl;

import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.Schedule;
import com.mobile.pomodoro.repositories.ReservationRepository;
import com.mobile.pomodoro.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService extends AService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    ReservationService(){
        initData();
    }

    @Override
    public void initData() {
        log.setName(this.getClass().getSimpleName());
        log.info("Initializing data");
    }

    @Override
    public List<Doctor> getDoctorsList() {
        log.info("Getting list of doctors...");

        try{
            Thread.sleep(2000);
            reservationRepository.findAllDoctors();
            log.info("List of doctors retrieved successfully.");
        } catch (Exception e) {
            log.error("Error while retrieving doctors list: " + e.getMessage());
            RuntimeException runtimeException = new RuntimeException("Error while retrieving doctors list");
            throw runtimeException;
        }

        return List.of();
    }

    @Override
    public List<Schedule> getDoctorSchedule(Doctor doctor, LocalDateTime date) {
        log.info("Getting schedule for doctor: " + doctor + " on date: " + date);

        try {
            Thread.sleep(2000);
            var schedules = reservationRepository.findSchedulesByDoctorId(doctor.getId());

            var emptySchedules = findEmptySchedules(schedules);

            log.info("Schedule with status 'empty' retrieved successfully.");
            return emptySchedules;

        } catch (Exception e) {
            log.error("Error while retrieving doctor's schedule: " + e.getMessage());
            RuntimeException runtimeException = new RuntimeException("Error while retrieving doctor's schedule");
            throw runtimeException;
        }
    }

    @Override
    public MessageResponseDTO saveReservation(Doctor doctor, LocalDateTime date, String patientName, String patientPhone) {
        log.info("Saving reservation for doctor: " + doctor + " on date: " + date);
        log.info("Patient name: " + patientName + ", Patient phone: " + patientPhone);
        MessageResponseDTO message;

        try {
            Thread.sleep(2000);
            reservationRepository.saveReservation(doctor.getId(), date, patientName, patientPhone);
            message = new MessageResponseDTO("Reservation saved successfully");
            log.info("Reservation saved successfully with 0 error.");
        } catch (Exception e) {
            log.error("Error while saving reservation: " + e.getMessage());
            message = new MessageResponseDTO("Error while saving reservation");
        }

        return message;
    }

    private List<Schedule> findEmptySchedules(List<Schedule> schedules) {
        return schedules.stream()
                .filter(schedule -> schedule.getStatus().equals("empty"))
                .toList();
    }
}
