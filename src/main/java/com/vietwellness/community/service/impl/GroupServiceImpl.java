// GroupServiceImpl.java
package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Group;
import com.vietwellness.community.repository.GroupRepository;
import com.vietwellness.community.service.GroupService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(Group group){
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> getGroupById(Long id){
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }

    @Override
    public Group updateGroup(Group group){
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id){
        groupRepository.deleteById(id);
    }
}
