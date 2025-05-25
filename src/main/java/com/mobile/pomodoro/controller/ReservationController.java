package com.mobile.pomodoro.controller;

import com.mobile.pomodoro.dto.request.ReservationRequestDTO;
import com.mobile.pomodoro.dto.request.ScheduleRequestDTO;
import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.MedicalFacility;
import com.mobile.pomodoro.entities.Schedule;
import com.mobile.pomodoro.services.IReservationService;
import com.mobile.pomodoro.services.impl.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các endpoint liên quan đến đặt lịch
 * 1.3.2. Chọn chức năng "Đặt lịch hẹn khám" trên giao diện.
 */
@RestController
@RequestMapping("/api/v1/reserve")
@RequiredArgsConstructor
public class ReservationController {
    @Autowired
    IReservationService reservationService;

    /**
     * Endpoint để mô phỏng đăng nhập người dùng (cho mục đích kiểm thử)
     * 1.3.1. Đăng nhập vào hệ thống WELLNET.
     */
    @PostMapping("/login")
    public ResponseEntity<MessageResponseDTO> login() {
        try {
            // Ép kiểu về lớp triển khai để truy cập phương thức setUserLoggedIn
            ((ReservationService) reservationService).setUserLoggedIn(true);
            return new ResponseEntity<>(new MessageResponseDTO("User logged in successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO("Failed to login: " + e.getMessage()), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint để mô phỏng đăng xuất người dùng (cho mục đích kiểm thử)
     */
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDTO> logout() {
        try {
            // Ép kiểu về lớp triển khai để truy cập phương thức setUserLoggedIn
            ((ReservationService) reservationService).setUserLoggedIn(false);
            return new ResponseEntity<>(new MessageResponseDTO("User logged out successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO("Failed to logout: " + e.getMessage()), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy danh sách tất cả các bác sĩ
     * 1.3.3. Hiển thị danh sách bác sĩ và cơ sở y tế đang hỗ trợ đặt lịch.
     */
    @GetMapping("/get-doctors")
    public ResponseEntity<?> getDoctorsList() {
        try {
            List<Doctor> doctors = reservationService.getDoctorsList();
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy danh sách tất cả các cơ sở y tế
     * 1.3.3. Hiển thị danh sách bác sĩ và cơ sở y tế đang hỗ trợ đặt lịch.
     */
    @GetMapping("/get-facilities")
    public ResponseEntity<?> getMedicalFacilities() {
        try {
            List<MedicalFacility> facilities = reservationService.getMedicalFacilities();
            return new ResponseEntity<>(facilities, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy lịch của bác sĩ cho một ngày cụ thể
     * 1.3.4. Chọn bác sĩ, ngày và giờ khám.
     * 1.3.5. Kiểm tra lịch trống của bác sĩ trong khoảng thời gian được chọn.
     */
    @PostMapping("/get-schedules")
    public ResponseEntity<?> getDoctorSchedule(@RequestBody ScheduleRequestDTO request) {
        try {
            List<Schedule> schedules = reservationService.getDoctorSchedule(request.getDoctor(), request.getDate());
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy các khung giờ thay thế khi khung giờ ưu tiên không khả dụng
     * 1.3.9. Nếu không có lịch trống, gợi ý các khung giờ khác gần nhất.
     */
    @PostMapping("/get-alternative-slots")
    public ResponseEntity<?> getAlternativeTimeSlots(@RequestBody ScheduleRequestDTO request) {
        try {
            List<Schedule> alternativeSlots = reservationService.getAlternativeTimeSlots(
                    request.getDoctor(), request.getDate());
            return new ResponseEntity<>(alternativeSlots, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lưu một lịch hẹn
     * 1.3.6. Hiển thị thông tin cuộc hẹn để người dùng xác nhận.
     * 1.3.7. Lưu thông tin lịch hẹn vào cơ sở dữ liệu.
     */
    @PutMapping("/save-reservation")
    public ResponseEntity<MessageResponseDTO> saveReservation(@RequestBody ReservationRequestDTO request) {
        try {
            var message = reservationService.saveReservation(
                    request.getDoctor(), 
                    request.getDate(), 
                    request.getPatientName(), 
                    request.getPatientPhone());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
