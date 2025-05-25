package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.User;
import com.vietwellness.community.repository.UserRepository;
import com.vietwellness.community.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của UserService.
 * Xử lý nghiệp vụ liên quan đến người dùng (User).
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor để inject UserRepository.
     * @param userRepository repository thao tác với User
     */
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Tạo người dùng mới.
     * @param user đối tượng User cần lưu
     * @return User đã được lưu
     */
    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    /**
     * Lấy người dùng theo id.
     * @param id id của người dùng
     * @return Optional chứa User nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả người dùng.
     * @return danh sách User
     */
    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Cập nhật thông tin người dùng.
     * @param user User cần cập nhật
     * @return User đã được cập nhật
     */
    @Override
    public User updateUser(User user){
        return userRepository.save(user);
    }

    /**
     * Xóa người dùng theo id.
     * @param id id của người dùng cần xóa
     */
    @Override
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
