package com.mobile.pomodoro.repositories;

import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.MedicalFacility;
import com.mobile.pomodoro.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT d FROM Doctor d")
    List<Doctor> findAllDoctors();

    @Query("SELECT m FROM MedicalFacility m")
    List<MedicalFacility> findAllMedicalFacilities();

    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId")
    List<Schedule> findSchedulesByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.id = :doctorId AND s.dateTime > :date ORDER BY s.dateTime ASC")
    List<Schedule> findAlternativeTimeSlots(@Param("doctorId") Long doctorId, @Param("date") LocalDateTime date);

    @Modifying
    @Transactional
    @Query("UPDATE Schedule s SET s.status = 'booked', s.patientName = :patientName, " +
           "s.patientPhone = :patientPhone WHERE s.doctor.id = :doctorId AND " +
           "s.dateTime = :dateTime AND s.status = 'empty'")
    void saveReservation(@Param("doctorId") Long doctorId, 
                         @Param("dateTime") LocalDateTime dateTime,
                         @Param("patientName") String patientName,
                         @Param("patientPhone") String patientPhone);
}
