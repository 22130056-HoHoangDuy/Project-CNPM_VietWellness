package com.mobile.pomodoro.services;

import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.MedicalFacility;
import com.mobile.pomodoro.entities.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService extends IInitializerData {

    /**
     * Get list of all doctors
     * @return List of doctors
     */
    List<Doctor> getDoctorsList();

    /**
     * Get list of all medical facilities
     * @return List of medical facilities
     */
    List<MedicalFacility> getMedicalFacilities();

    /**
     * Get doctor's schedule for a specific date
     * @param doctor The doctor
     * @param date The date
     * @return List of available schedules
     */
    List<Schedule> getDoctorSchedule(Doctor doctor, LocalDateTime date);

    /**
     * Get alternative time slots when preferred slot is not available
     * @param doctor The doctor
     * @param date The preferred date
     * @return List of alternative available schedules
     */
    List<Schedule> getAlternativeTimeSlots(Doctor doctor, LocalDateTime date);

    /**
     * Save a reservation
     * @param doctor The doctor
     * @param date The date and time
     * @param patientName Patient's name
     * @param patientPhone Patient's phone number
     * @return Message response with status
     */
    MessageResponseDTO saveReservation(Doctor doctor, LocalDateTime date, String patientName, String patientPhone);

    /**
     * Send notification to user and doctor about the reservation
     * @param doctor The doctor
     * @param date The date and time
     * @param patientName Patient's name
     * @param patientPhone Patient's phone number
     * @return Message response with status
     */
    MessageResponseDTO sendNotification(Doctor doctor, LocalDateTime date, String patientName, String patientPhone);

    /**
     * Check if user is logged in
     * @return true if logged in, false otherwise
     */
    boolean isUserLoggedIn();
}
