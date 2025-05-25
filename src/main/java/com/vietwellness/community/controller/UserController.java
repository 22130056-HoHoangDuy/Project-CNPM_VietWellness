package com.vietwellness.community.controller;

import com.vietwellness.community.entity.User;
import com.vietwellness.community.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các API liên quan đến người dùng (User).
 *
 * Đường dẫn gốc: /api/users
 * Cung cấp các chức năng:
 * - Tạo người dùng
 * - Xem chi tiết người dùng theo ID
 * - Lấy danh sách tất cả người dùng
 * - Cập nhật thông tin người dùng
 * - Xoá người dùng
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor để inject UserService vào controller.
     *
     * @param userService Dịch vụ xử lý logic người dùng
     */
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * API tạo mới một người dùng.
     *
     * @param user Đối tượng người dùng nhận từ request body
     * @return Người dùng sau khi được tạo thành công
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    /**
     * API lấy thông tin người dùng theo ID.
     *
     * @param id ID của người dùng
     * @return Trả về người dùng nếu tìm thấy, ngược lại trả về 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả người dùng trong hệ thống.
     *
     * @return Danh sách người dùng
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * API cập nhật thông tin của một người dùng.
     *
     * @param id ID của người dùng cần cập nhật
     * @param user Dữ liệu người dùng mới
     * @return Người dùng đã được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id); // Gán ID đúng cho đối tượng cần cập nhật
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(updated);
    }

    /**
     * API xoá một người dùng theo ID.
     *
     * @param id ID của người dùng cần xoá
     * @return Trả về 204 No Content nếu xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
