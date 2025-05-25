package com.vietwellness.community.controller;

import com.vietwellness.community.entity.Group;
import com.vietwellness.community.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các API liên quan đến Group (nhóm cộng đồng).
 *
 * URL cơ bản: /api/groups
 *
 * Các chức năng chính:
 * - Tạo mới nhóm
 * - Lấy nhóm theo ID
 * - Lấy danh sách tất cả nhóm
 * - Cập nhật nhóm
 * - Xoá nhóm
 */
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    /**
     * Constructor để inject GroupService vào controller.
     *
     * @param groupService Dịch vụ xử lý logic nhóm
     */
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    /**
     * API tạo mới một nhóm.
     *
     * @param group Đối tượng nhóm gửi từ client
     * @return Nhóm sau khi được tạo thành công
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group){
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.ok(createdGroup);
    }

    /**
     * API lấy thông tin nhóm theo ID.
     *
     * @param id ID của nhóm cần lấy
     * @return Nhóm nếu tìm thấy, hoặc trả về 404 nếu không tồn tại
     */
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id){
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả nhóm.
     *
     * @return Danh sách các nhóm hiện có
     */
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(){
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * API cập nhật thông tin nhóm theo ID.
     *
     * @param id ID của nhóm cần cập nhật
     * @param group Dữ liệu nhóm mới
     * @return Nhóm sau khi được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group){
        group.setId(id); // Gán ID vào đối tượng để đảm bảo đúng nhóm cần cập nhật
        Group updatedGroup = groupService.updateGroup(group);
        return ResponseEntity.ok(updatedGroup);
    }

    /**
     * API xoá một nhóm theo ID.
     *
     * @param id ID của nhóm cần xoá
     * @return Trạng thái thành công (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
