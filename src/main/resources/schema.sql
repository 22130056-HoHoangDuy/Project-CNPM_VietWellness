-- MySQL script to create tables for the Pomodoro application

-- Drop tables if they exist (in reverse order of creation to respect foreign key constraints)
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS medical_facilities;

-- Create medical_facilities table
CREATE TABLE medical_facilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(255)
);

-- Create doctors table
CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    medical_facility_id BIGINT,
    FOREIGN KEY (medical_facility_id) REFERENCES medical_facilities(id)
);

-- Create schedules table
CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    date_time DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    patient_name VARCHAR(255),
    patient_phone VARCHAR(20),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

-- Add indexes for better performance
CREATE INDEX idx_doctor_medical_facility ON doctors(medical_facility_id);
CREATE INDEX idx_schedule_doctor ON schedules(doctor_id);
CREATE INDEX idx_schedule_date_time ON schedules(date_time);
CREATE INDEX idx_schedule_status ON schedules(status);