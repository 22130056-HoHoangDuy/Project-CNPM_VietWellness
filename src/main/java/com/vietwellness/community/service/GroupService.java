package com.vietwellness.community.service;

import com.vietwellness.community.entity.Group;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức nghiệp vụ liên quan đến Group (Nhóm).
 */
public interface GroupService {

    /**
     * Tạo mới một nhóm.
     * @param group đối tượng Group cần tạo
     * @return Group đã được tạo
     */
    Group createGroup(Group group);

    /**
     * Lấy thông tin nhóm theo id.
     * @param id id của nhóm
     * @return Optional chứa Group nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<Group> getGroupById(Long id);

    /**
     * Lấy danh sách tất cả nhóm.
     * @return danh sách Group
     */
    List<Group> getAllGroups();

    /**
     * Cập nhật thông tin nhóm.
     * @param group đối tượng Group cần cập nhật
     * @return Group đã được cập nhật
     */
    Group updateGroup(Group group);

    /**
     * Xóa nhóm theo id.
     * @param id id của nhóm cần xóa
     */
    void deleteGroup(Long id);
}
