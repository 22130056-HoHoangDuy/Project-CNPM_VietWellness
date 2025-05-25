package com.mobile.pomodoro.controller;

import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.Schedule;
import com.mobile.pomodoro.services.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reserve")
@RequiredArgsConstructor
public class ReservationController {
    @Autowired
    IReservationService reservationService;

    @GetMapping("/get-doctors")
    public ResponseEntity<List<Doctor>> getDoctorsList() {
        try {
            List<Doctor> doctors = reservationService.getDoctorsList();
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-schedules")
    public ResponseEntity<List<Schedule>> getDoctorSchedule(@RequestBody Doctor doctor,
                                                            @RequestBody LocalDateTime date) {
        try {
            List<Schedule> schedules = reservationService.getDoctorSchedule(doctor, date);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/save-reservation")
    public ResponseEntity<MessageResponseDTO> saveReservation(@RequestBody Doctor doctor,
                                                              @RequestBody LocalDateTime date,
                                                              @RequestBody String patientName,
                                                              @RequestBody String patientPhone) {

        var message = reservationService.saveReservation(doctor, date, patientName, patientPhone);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}
