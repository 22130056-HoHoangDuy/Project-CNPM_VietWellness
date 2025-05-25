package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.GroupMembership;
import com.vietwellness.community.repository.GroupMembershipRepository;
import com.vietwellness.community.service.GroupMembershipService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của GroupMembershipService.
 * Xử lý nghiệp vụ liên quan đến thành viên nhóm.
 */
@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final GroupMembershipRepository groupMembershipRepository;

    /**
     * Constructor để inject GroupMembershipRepository.
     * @param groupMembershipRepository repository thao tác với GroupMembership
     */
    public GroupMembershipServiceImpl(GroupMembershipRepository groupMembershipRepository){
        this.groupMembershipRepository = groupMembershipRepository;
    }

    /**
     * Thêm thành viên vào nhóm.
     * @param membership đối tượng GroupMembership mới
     * @return GroupMembership đã được lưu
     */
    @Override
    public GroupMembership addMember(GroupMembership membership){
        return groupMembershipRepository.save(membership);
    }

    /**
     * Lấy thành viên nhóm theo id.
     * @param id id của GroupMembership
     * @return Optional chứa GroupMembership nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<GroupMembership> getMembershipById(Long id){
        return groupMembershipRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả thành viên nhóm.
     * @return danh sách GroupMembership
     */
    @Override
    public List<GroupMembership> getAllMemberships(){
        return groupMembershipRepository.findAll();
    }

    /**
     * Cập nhật thông tin thành viên nhóm.
     * @param membership GroupMembership cần cập nhật
     * @return GroupMembership đã được cập nhật
     */
    @Override
    public GroupMembership updateMembership(GroupMembership membership){
        return groupMembershipRepository.save(membership);
    }

    /**
     * Xóa thành viên nhóm theo id.
     * @param id id của GroupMembership cần xóa
     */
    @Override
    public void removeMember(Long id){
        groupMembershipRepository.deleteById(id);
    }
}
