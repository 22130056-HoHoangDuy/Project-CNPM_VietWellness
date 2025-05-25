package com.vietwellness.community.controller;

import com.vietwellness.community.entity.GroupMembership;
import com.vietwellness.community.service.GroupMembershipService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-memberships")
public class GroupMembershipController {

    private final GroupMembershipService groupMembershipService;

    public GroupMembershipController(GroupMembershipService groupMembershipService){
        this.groupMembershipService = groupMembershipService;
    }

    @PostMapping
    public ResponseEntity<GroupMembership> addMember(@RequestBody GroupMembership membership){
        GroupMembership added = groupMembershipService.addMember(membership);
        return ResponseEntity.ok(added);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMembership> getMembershipById(@PathVariable Long id){
        return groupMembershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GroupMembership>> getAllMemberships(){
        return ResponseEntity.ok(groupMembershipService.getAllMemberships());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupMembership> updateMembership(@PathVariable Long id, @RequestBody GroupMembership membership){
        membership.setId(id);
        GroupMembership updated = groupMembershipService.updateMembership(membership);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id){
        groupMembershipService.removeMember(id);
        return ResponseEntity.noContent().build();
    }
}
