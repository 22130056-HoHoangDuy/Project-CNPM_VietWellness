package com.vietwellness.community.repository;

import com.vietwellness.community.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {}
