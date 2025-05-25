package org.example.Service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Entity.AccessGrant;
import org.example.Entity.User;
import org.example.Service.AccessGrantRepository;
import org.example.Service.IPrivacyService;
import org.example.Service.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class  PrivacyServiceimpl  implements IPrivacyService {
@Autowired
UserRepository userRepository;
@Autowired
AccessGrantRepository accessGrantRepository;
    @PostConstruct
    public void initData() {
        log.info("Seeding default data...");
    }

    @Override
    public List<AccessGrant> getAccessGrants(String username) {
        log.info("Fetching access grants for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new RuntimeException("User not found");
                });

        List<AccessGrant> grants = accessGrantRepository.findByUser(user);
        grants.forEach(grant -> Hibernate.initialize(grant.getUser())); // Khởi tạo user

        log.info("Found {} access grants for user: {}", grants.size(), username);
        return grants;
    }

    @Override
    public void revokeAccess(String username, String granteeName) {
        log.info("Attempting to revoke access for grantee: {} from user: {}", granteeName, username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new RuntimeException("User not found");
                });

        List<AccessGrant> grants = accessGrantRepository.findByUser(user);
        AccessGrant toRevoke = grants.stream()
                .filter(g -> g.getGranteeName().equalsIgnoreCase(granteeName))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Access grant not found for grantee: {} and user: {}", granteeName, username);
                    return new RuntimeException("Access not found");
                });

        accessGrantRepository.delete(toRevoke);
        log.info("Successfully revoked access for grantee: {} from user: {}", granteeName, username);
    }
}
