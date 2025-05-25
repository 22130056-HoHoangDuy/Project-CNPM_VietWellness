// GroupService.java
package com.vietwellness.community.service;

import com.vietwellness.community.entity.Group;
import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(Group group);
    Optional<Group> getGroupById(Long id);
    List<Group> getAllGroups();
    Group updateGroup(Group group);
    void deleteGroup(Long id);
}
