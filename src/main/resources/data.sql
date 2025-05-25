-- Users
INSERT INTO users (username, email, avatar_url) VALUES 
('duy', 'duy@example.com', 'https://example.com/avatar_duy.jpg'),
('anh', 'anh@example.com', NULL),
('hoa', 'hoa@example.com', 'https://example.com/avatar_hoa.jpg'),
('minh', 'minh@example.com', NULL),
('linh', 'linh@example.com', 'https://example.com/avatar_linh.jpg');

-- Groups
INSERT INTO groups (name, description, privacy) VALUES 
('Nhóm Sức Khỏe', 'Chia sẻ kiến thức sức khỏe tổng hợp', 'PUBLIC'),
('Nhóm Hỗ Trợ Bệnh Tiểu Đường', 'Nhóm kín cho bệnh nhân tiểu đường', 'PRIVATE'),
('Nhóm Yoga và Thiền', 'Cộng đồng tập Yoga và Thiền', 'PUBLIC');

-- Posts
INSERT INTO posts (content, media_url, status, author_id, group_id, created_at) VALUES
('Bài viết về lợi ích của việc tập thể dục đều đặn', NULL, 'APPROVED', 1, 1, NOW()),
('Chia sẻ kinh nghiệm kiểm soát đường huyết', NULL, 'APPROVED', 2, 2, NOW()),
('Video hướng dẫn bài tập yoga căn bản', 'https://example.com/yoga_video.mp4', 'NEEDS_VERIFICATION', 3, 3, NOW());

-- Comments
INSERT INTO comments (content, commenter_id, post_id, commented_at) VALUES
('Rất hữu ích, cảm ơn bạn!', 4, 1, NOW()),
('Mình cũng đang áp dụng, hiệu quả lắm!', 5, 2, NOW()),
('Video này chất lượng quá!', 1, 3, NOW()),
('Có bài tập cho người mới bắt đầu không?', 4, 3, NOW());

-- Group Memberships
INSERT INTO group_memberships (member_id, group_id, approved) VALUES
(4, 2, FALSE), -- minh yêu cầu tham gia nhóm tiểu đường, chờ duyệt
(5, 2, TRUE),  -- linh đã được duyệt tham gia nhóm tiểu đường
(1, 3, TRUE);  -- duy tham gia nhóm yoga

-- Reports
INSERT INTO reports (reason, reported_post_id, reported_by_id, reported_at) VALUES
('Nội dung sai sự thật về sức khỏe', 3, 5, NOW()),
('Spam quảng cáo không liên quan', 2, 4, NOW());
-- Sample data for the Pomodoro application

-- Insert sample medical facilities
INSERT INTO medical_facilities (name, address, phone_number, email) VALUES
('City General Hospital', '123 Main Street, City Center', '123-456-7890', 'info@citygeneral.com'),
('Westside Medical Center', '456 West Avenue, Westside', '234-567-8901', 'contact@westsidemedical.com'),
('Eastside Health Clinic', '789 East Boulevard, Eastside', '345-678-9012', 'info@eastsidehealth.com');

-- Insert sample doctors
INSERT INTO doctors (name, specialty, phone_number, email, medical_facility_id) VALUES
('Dr. John Smith', 'Cardiology', '111-222-3333', 'john.smith@citygeneral.com', 1),
('Dr. Sarah Johnson', 'Neurology', '222-333-4444', 'sarah.johnson@citygeneral.com', 1),
('Dr. Michael Brown', 'Orthopedics', '333-444-5555', 'michael.brown@westsidemedical.com', 2),
('Dr. Emily Davis', 'Pediatrics', '444-555-6666', 'emily.davis@westsidemedical.com', 2),
('Dr. Robert Wilson', 'Dermatology', '555-666-7777', 'robert.wilson@eastsidehealth.com', 3),
('Dr. Jennifer Lee', 'Family Medicine', '666-777-8888', 'jennifer.lee@eastsidehealth.com', 3);

-- Insert sample schedules (current date + 1-14 days, with various time slots)
-- Using function to generate dates would be better, but for simplicity, we'll use explicit dates
-- Status can be 'empty' or 'booked'

-- Dr. John Smith's schedule (ID: 1)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(1, '2023-12-01 09:00:00', 'empty', NULL, NULL),
(1, '2023-12-01 10:00:00', 'booked', 'Alice Johnson', '111-111-1111'),
(1, '2023-12-01 11:00:00', 'empty', NULL, NULL),
(1, '2023-12-02 09:00:00', 'empty', NULL, NULL),
(1, '2023-12-02 10:00:00', 'empty', NULL, NULL);

-- Dr. Sarah Johnson's schedule (ID: 2)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(2, '2023-12-01 13:00:00', 'empty', NULL, NULL),
(2, '2023-12-01 14:00:00', 'booked', 'Bob Smith', '222-222-2222'),
(2, '2023-12-01 15:00:00', 'empty', NULL, NULL),
(2, '2023-12-02 13:00:00', 'empty', NULL, NULL),
(2, '2023-12-02 14:00:00', 'empty', NULL, NULL);

-- Dr. Michael Brown's schedule (ID: 3)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(3, '2023-12-01 09:00:00', 'booked', 'Charlie Davis', '333-333-3333'),
(3, '2023-12-01 10:00:00', 'empty', NULL, NULL),
(3, '2023-12-01 11:00:00', 'empty', NULL, NULL),
(3, '2023-12-02 09:00:00', 'empty', NULL, NULL),
(3, '2023-12-02 10:00:00', 'empty', NULL, NULL);

-- Dr. Emily Davis's schedule (ID: 4)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(4, '2023-12-01 13:00:00', 'empty', NULL, NULL),
(4, '2023-12-01 14:00:00', 'empty', NULL, NULL),
(4, '2023-12-01 15:00:00', 'booked', 'David Wilson', '444-444-4444'),
(4, '2023-12-02 13:00:00', 'empty', NULL, NULL),
(4, '2023-12-02 14:00:00', 'empty', NULL, NULL);

-- Dr. Robert Wilson's schedule (ID: 5)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(5, '2023-12-01 09:00:00', 'empty', NULL, NULL),
(5, '2023-12-01 10:00:00', 'empty', NULL, NULL),
(5, '2023-12-01 11:00:00', 'booked', 'Eva Brown', '555-555-5555'),
(5, '2023-12-02 09:00:00', 'empty', NULL, NULL),
(5, '2023-12-02 10:00:00', 'empty', NULL, NULL);

-- Dr. Jennifer Lee's schedule (ID: 6)
INSERT INTO schedules (doctor_id, date_time, status, patient_name, patient_phone) VALUES
(6, '2023-12-01 13:00:00', 'booked', 'Frank Miller', '666-666-6666'),
(6, '2023-12-01 14:00:00', 'empty', NULL, NULL),
(6, '2023-12-01 15:00:00', 'empty', NULL, NULL),
(6, '2023-12-02 13:00:00', 'empty', NULL, NULL),
(6, '2023-12-02 14:00:00', 'empty', NULL, NULL);