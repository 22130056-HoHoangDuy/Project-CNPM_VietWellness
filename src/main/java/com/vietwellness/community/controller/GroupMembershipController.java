package com.vietwellness.community.controller;

import com.vietwellness.community.entity.GroupMembership;
import com.vietwellness.community.service.GroupMembershipService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý API cho việc tham gia hoặc quản lý thành viên trong nhóm.
 *
 * URL cơ bản: /api/group-memberships
 *
 * Các chức năng chính:
 * - Thêm thành viên vào nhóm
 * - Lấy thông tin thành viên theo ID
 * - Lấy danh sách tất cả thành viên trong nhóm
 * - Cập nhật thông tin thành viên
 * - Xoá thành viên khỏi nhóm
 */
@RestController
@RequestMapping("/api/group-memberships")
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;

    /**
     * Constructor để inject GroupMembershipService vào controller.
     *
     * @param groupMembershipService Dịch vụ xử lý logic liên quan đến thành viên nhóm
     */
    public GroupMembershipController(GroupMembershipService groupMembershipService){
        this.groupMembershipService = groupMembershipService;
    }

    /**
     * API thêm thành viên mới vào nhóm.
     *
     * @param membership Thông tin thành viên (bao gồm ID nhóm và ID người dùng)
     * @return Đối tượng thành viên sau khi thêm thành công
     */
    @PostMapping
    public ResponseEntity<GroupMembership> addMember(@RequestBody GroupMembership membership){
        GroupMembership added = groupMembershipService.addMember(membership);
        return ResponseEntity.ok(added);
    }

    /**
     * API lấy thông tin thành viên nhóm theo ID.
     *
     * @param id ID của membership
     * @return Trả về thành viên nếu tìm thấy, hoặc 404 nếu không tồn tại
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroupMembership> getMembershipById(@PathVariable Long id){
        return groupMembershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả các thành viên trong tất cả các nhóm.
     *
     * @return Danh sách tất cả thành viên nhóm
     */
    @GetMapping
    public ResponseEntity<List<GroupMembership>> getAllMemberships(){
        return ResponseEntity.ok(groupMembershipService.getAllMemberships());
    }

    /**
     * API cập nhật thông tin thành viên nhóm.
     *
     * @param id ID của thành viên cần cập nhật
     * @param membership Thông tin thành viên mới
     * @return Đối tượng thành viên sau khi cập nhật thành công
     */
    @PutMapping("/{id}")
    public ResponseEntity<GroupMembership> updateMembership(@PathVariable Long id, @RequestBody GroupMembership membership){
        membership.setId(id); // Gán ID vào đối tượng để cập nhật đúng record
        GroupMembership updated = groupMembershipService.updateMembership(membership);
        return ResponseEntity.ok(updated);
    }

    /**
     * API xoá thành viên khỏi nhóm.
     *
     * @param id ID của membership cần xoá
     * @return Trả về 204 No Content nếu xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id){
        groupMembershipService.removeMember(id);
        return ResponseEntity.noContent().build();
    }
}
