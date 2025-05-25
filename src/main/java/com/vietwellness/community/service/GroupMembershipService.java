package com.vietwellness.community.service;

import com.vietwellness.community.entity.GroupMembership;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức nghiệp vụ liên quan đến GroupMembership (Thành viên nhóm).
 */
public interface GroupMembershipService {

    /**
     * Thêm thành viên vào nhóm.
     * @param membership đối tượng GroupMembership cần thêm
     * @return GroupMembership đã được thêm
     */
    GroupMembership addMember(GroupMembership membership);

    /**
     * Lấy thông tin thành viên nhóm theo id.
     * @param id id của GroupMembership
     * @return Optional chứa GroupMembership nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<GroupMembership> getMembershipById(Long id);

    /**
     * Lấy danh sách tất cả thành viên nhóm.
     * @return danh sách GroupMembership
     */
    List<GroupMembership> getAllMemberships();

    /**
     * Cập nhật thông tin thành viên nhóm.
     * @param membership đối tượng GroupMembership cần cập nhật
     * @return GroupMembership đã được cập nhật
     */
    GroupMembership updateMembership(GroupMembership membership);

    /**
     * Xóa thành viên nhóm theo id.
     * @param id id của GroupMembership cần xóa
     */
    void removeMember(Long id);
}
