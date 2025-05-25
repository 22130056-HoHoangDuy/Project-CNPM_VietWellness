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
