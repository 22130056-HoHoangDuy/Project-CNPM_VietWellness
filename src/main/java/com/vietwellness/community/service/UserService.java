package com.vietwellness.community.service;

import com.vietwellness.community.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức nghiệp vụ liên quan đến User (Người dùng).
 */
public interface UserService {

    /**
     * Tạo mới một người dùng.
     * @param user đối tượng User cần tạo
     * @return User đã được tạo
     */
    User createUser(User user);

    /**
     * Lấy thông tin người dùng theo id.
     * @param id id của người dùng
     * @return Optional chứa User nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<User> getUserById(Long id);

    /**
     * Lấy danh sách tất cả người dùng.
     * @return danh sách User
     */
    List<User> getAllUsers();

    /**
     * Cập nhật thông tin người dùng.
     * @param user đối tượng User cần cập nhật
     * @return User đã được cập nhật
     */
    User updateUser(User user);

    /**
     * Xóa người dùng theo id.
     * @param id id của người dùng cần xóa
     */
    void deleteUser(Long id);
}
