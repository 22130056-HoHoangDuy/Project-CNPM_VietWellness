// GroupMembershipServiceImpl.java
package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.GroupMembership;
import com.vietwellness.community.repository.GroupMembershipRepository;
import com.vietwellness.community.service.GroupMembershipService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {

    private final GroupMembershipRepository groupMembershipRepository;

    public GroupMembershipServiceImpl(GroupMembershipRepository groupMembershipRepository){
        this.groupMembershipRepository = groupMembershipRepository;
    }

    @Override
    public GroupMembership addMember(GroupMembership membership){
        return groupMembershipRepository.save(membership);
    }

    @Override
    public Optional<GroupMembership> getMembershipById(Long id){
        return groupMembershipRepository.findById(id);
    }

    @Override
    public List<GroupMembership> getAllMemberships(){
        return groupMembershipRepository.findAll();
    }

    @Override
    public GroupMembership updateMembership(GroupMembership membership){
        return groupMembershipRepository.save(membership);
    }

    @Override
    public void removeMember(Long id){
        groupMembershipRepository.deleteById(id);
    }
}
