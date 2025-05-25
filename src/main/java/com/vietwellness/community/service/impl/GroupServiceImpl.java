package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Group;
import com.vietwellness.community.repository.GroupRepository;
import com.vietwellness.community.service.GroupService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của GroupService.
 * Xử lý nghiệp vụ liên quan đến nhóm (Group).
 */
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    /**
     * Constructor để inject GroupRepository.
     * @param groupRepository repository thao tác với Group
     */
    public GroupServiceImpl(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    /**
     * Tạo nhóm mới.
     * @param group đối tượng Group mới cần lưu
     * @return Group đã được lưu
     */
    @Override
    public Group createGroup(Group group){
        return groupRepository.save(group);
    }

    /**
     * Lấy nhóm theo id.
     * @param id id của nhóm
     * @return Optional chứa Group nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<Group> getGroupById(Long id){
        return groupRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả nhóm.
     * @return danh sách Group
     */
    @Override
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    /**
     * Cập nhật thông tin nhóm.
     * @param group Group cần cập nhật
     * @return Group đã được cập nhật
     */
    @Override
    public Group updateGroup(Group group){
        return groupRepository.save(group);
    }

    /**
     * Xóa nhóm theo id.
     * @param id id của nhóm cần xóa
     */
    @Override
    public void deleteGroup(Long id){
        groupRepository.deleteById(id);
    }
}
