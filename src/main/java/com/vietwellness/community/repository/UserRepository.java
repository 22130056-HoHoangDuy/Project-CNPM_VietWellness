package com.vietwellness.community.repository;

import com.vietwellness.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
}
