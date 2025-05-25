// GroupMembershipService.java
package com.vietwellness.community.service;

import com.vietwellness.community.entity.GroupMembership;
import java.util.List;
import java.util.Optional;

public interface GroupMembershipService {
    GroupMembership addMember(GroupMembership membership);
    Optional<GroupMembership> getMembershipById(Long id);
    List<GroupMembership> getAllMemberships();
    GroupMembership updateMembership(GroupMembership membership);
    void removeMember(Long id);
}
