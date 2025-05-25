package com.mobile.pomodoro.services.impl;

import com.mobile.pomodoro.dto.response.MessageResponseDTO;
import com.mobile.pomodoro.entities.Doctor;
import com.mobile.pomodoro.entities.MedicalFacility;
import com.mobile.pomodoro.entities.Schedule;
import com.mobile.pomodoro.repositories.ReservationRepository;
import com.mobile.pomodoro.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService extends AService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // 1.3.1. Đăng nhập vào hệ thống WELLNET.
    private boolean userLoggedIn = false;

    ReservationService(){
        initData();
    }

    @Override
    public void initData() {
        log.setName(this.getClass().getSimpleName());
        log.info("Khởi tạo dữ liệu");
    }

    @Override
    // 1.3.3. Hiển thị danh sách bác sĩ và cơ sở y tế đang hỗ trợ đặt lịch.
    public List<Doctor> getDoctorsList() {
        log.info("Đang lấy danh sách bác sĩ...");

        try{
            // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
            if (!isUserLoggedIn()) {
                log.error("Người dùng chưa đăng nhập");
                throw new RuntimeException("Người dùng phải đăng nhập để sử dụng tính năng này");
            }

            Thread.sleep(2000);
            var doctors = reservationRepository.findAllDoctors();
            log.info("Danh sách bác sĩ đã được lấy thành công.");
            return doctors;
        } catch (InterruptedException e) {
            log.error("Lỗi khi lấy danh sách bác sĩ: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lấy danh sách bác sĩ");
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách bác sĩ: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                throw new RuntimeException("Lỗi kết nối Internet. Vui lòng thử lại.");
            }
            throw new RuntimeException("Lỗi khi lấy danh sách bác sĩ");
        }
    }

    @Override
    // 1.3.3. Hiển thị danh sách bác sĩ và cơ sở y tế đang hỗ trợ đặt lịch.
    public List<MedicalFacility> getMedicalFacilities() {
        log.info("Đang lấy danh sách cơ sở y tế...");

        try{
            // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
            if (!isUserLoggedIn()) {
                log.error("Người dùng chưa đăng nhập");
                throw new RuntimeException("Người dùng phải đăng nhập để sử dụng tính năng này");
            }

            Thread.sleep(2000);
            var facilities = reservationRepository.findAllMedicalFacilities();
            log.info("Danh sách cơ sở y tế đã được lấy thành công.");
            return facilities;
        } catch (InterruptedException e) {
            log.error("Lỗi khi lấy danh sách cơ sở y tế: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lấy danh sách cơ sở y tế");
        } catch (Exception e) {
            log.error("Lỗi khi lấy danh sách cơ sở y tế: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                throw new RuntimeException("Lỗi kết nối Internet. Vui lòng thử lại.");
            }
            throw new RuntimeException("Lỗi khi lấy danh sách cơ sở y tế");
        }
    }

    @Override
    // 1.3.4. Chọn bác sĩ, ngày và giờ khám.
    // 1.3.5. Kiểm tra lịch trống của bác sĩ trong khoảng thời gian được chọn.
    public List<Schedule> getDoctorSchedule(Doctor doctor, LocalDateTime date) {
        log.info("Đang lấy lịch cho bác sĩ: " + doctor + " vào ngày: " + date);

        try {
            // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
            if (!isUserLoggedIn()) {
                log.error("Người dùng chưa đăng nhập");
                throw new RuntimeException("Người dùng phải đăng nhập để sử dụng tính năng này");
            }

            Thread.sleep(2000);
            var schedules = reservationRepository.findSchedulesByDoctorId(doctor.getId());

            var emptySchedules = findEmptySchedules(schedules);

            log.info("Lịch với trạng thái 'trống' đã được lấy thành công.");
            return emptySchedules;

        } catch (InterruptedException e) {
            log.error("Lỗi khi lấy lịch của bác sĩ: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lấy lịch của bác sĩ");
        } catch (Exception e) {
            log.error("Lỗi khi lấy lịch của bác sĩ: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                throw new RuntimeException("Lỗi kết nối Internet. Vui lòng thử lại.");
            }
            throw new RuntimeException("Lỗi khi lấy lịch của bác sĩ");
        }
    }

    @Override
    // 1.3.9. Nếu không có lịch trống, gợi ý các khung giờ khác gần nhất.
    public List<Schedule> getAlternativeTimeSlots(Doctor doctor, LocalDateTime date) {
        log.info("Đang lấy các khung giờ thay thế cho bác sĩ: " + doctor + " sau ngày: " + date);

        try {
            // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
            if (!isUserLoggedIn()) {
                log.error("Người dùng chưa đăng nhập");
                throw new RuntimeException("Người dùng phải đăng nhập để sử dụng tính năng này");
            }

            Thread.sleep(2000);
            var alternativeSlots = reservationRepository.findAlternativeTimeSlots(doctor.getId(), date);

            var emptyAlternativeSlots = findEmptySchedules(alternativeSlots);

            log.info("Các khung giờ thay thế đã được lấy thành công.");
            return emptyAlternativeSlots;

        } catch (InterruptedException e) {
            log.error("Lỗi khi lấy các khung giờ thay thế: " + e.getMessage());
            throw new RuntimeException("Lỗi khi lấy các khung giờ thay thế");
        } catch (Exception e) {
            log.error("Lỗi khi lấy các khung giờ thay thế: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                throw new RuntimeException("Lỗi kết nối Internet. Vui lòng thử lại.");
            }
            throw new RuntimeException("Lỗi khi lấy các khung giờ thay thế");
        }
    }

    @Override
    // 1.3.6. Hiển thị thông tin cuộc hẹn để người dùng xác nhận.
    // 1.3.7. Lưu thông tin lịch hẹn vào cơ sở dữ liệu.
    public MessageResponseDTO saveReservation(Doctor doctor, LocalDateTime date, String patientName, String patientPhone) {
        log.info("Đang lưu lịch hẹn cho bác sĩ: " + doctor + " vào ngày: " + date);
        log.info("Tên bệnh nhân: " + patientName + ", Số điện thoại bệnh nhân: " + patientPhone);
        MessageResponseDTO message;

        try {
            // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
            if (!isUserLoggedIn()) {
                log.error("Người dùng chưa đăng nhập");
                return new MessageResponseDTO("Người dùng phải đăng nhập để sử dụng tính năng này");
            }

            Thread.sleep(2000);
            reservationRepository.saveReservation(doctor.getId(), date, patientName, patientPhone);
            message = new MessageResponseDTO("Lịch hẹn đã được lưu thành công");
            log.info("Lịch hẹn đã được lưu thành công không có lỗi.");

            // 1.3.8. Thông báo xác nhận lịch hẹn đến người dùng và bác sĩ qua ứng dụng/email.
            sendNotification(doctor, date, patientName, patientPhone);

        } catch (InterruptedException e) {
            log.error("Lỗi khi lưu lịch hẹn: " + e.getMessage());
            message = new MessageResponseDTO("Lỗi khi lưu lịch hẹn");
        } catch (Exception e) {
            log.error("Lỗi khi lưu lịch hẹn: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                message = new MessageResponseDTO("Lỗi kết nối Internet. Vui lòng thử lại.");
            } else {
                message = new MessageResponseDTO("Lỗi khi lưu lịch hẹn");
            }
        }

        return message;
    }

    @Override
    // 1.3.8. Thông báo xác nhận lịch hẹn đến người dùng và bác sĩ qua ứng dụng/email.
    public MessageResponseDTO sendNotification(Doctor doctor, LocalDateTime date, String patientName, String patientPhone) {
        log.info("Sending notification for reservation with doctor: " + doctor + " on date: " + date);
        log.info("To patient: " + patientName + " and doctor: " + doctor.getName());

        try {
            Thread.sleep(1000); // Simulate notification sending
            log.info("Notification sent successfully to patient via app/email");
            log.info("Notification sent successfully to doctor via app/email");
            return new MessageResponseDTO("Notification sent successfully");
        } catch (InterruptedException e) {
            log.error("Error while sending notification: " + e.getMessage());
            return new MessageResponseDTO("Error while sending notification");
        } catch (Exception e) {
            log.error("Error while sending notification: " + e.getMessage());
            // 1.3.11. Nếu mất kết nối Internet trong quá trình xác nhận, hệ thống hiển thị thông báo lỗi và yêu cầu thử lại.
            if (e instanceof ConnectException) {
                return new MessageResponseDTO("Internet connection error. Notification not sent.");
            }
            return new MessageResponseDTO("Error while sending notification");
        }
    }

    @Override
    // 1.3.10. Nếu người dùng chưa đăng nhập, hệ thống yêu cầu đăng nhập trước khi tiếp tục.
    public boolean isUserLoggedIn() {
        // In a real application, this would check the user's session or authentication token
        return userLoggedIn;
    }

    // 1.3.1. Đăng nhập vào hệ thống WELLNET.
    // Method to simulate user login (for testing purposes)
    public void setUserLoggedIn(boolean loggedIn) {
        this.userLoggedIn = loggedIn;
    }

    private List<Schedule> findEmptySchedules(List<Schedule> schedules) {
        return schedules.stream()
                .filter(schedule -> schedule.getStatus().equals("empty"))
                .toList();
    }
}
