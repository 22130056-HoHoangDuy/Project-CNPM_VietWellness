package org.example.Service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Entity.AccessGrant;
import org.example.Entity.User;
import org.example.Service.AccessGrantRepository;
import org.example.Service.IPrivacyService;
import org.example.Service.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivacyServiceImpl implements IPrivacyService {

    private final UserRepository userRepository;
    private final AccessGrantRepository accessGrantRepository;

    @PostConstruct
    public void initData() {
        log.info("Seeding default data...");
        // Có thể thêm dữ liệu mẫu nếu cần
        /*
        if (userRepository.count() == 0) {
            User user = new User(null, "testuser", "test@example.com", "1234567890", "password", false, null, new ArrayList<>());
            userRepository.save(user);
            AccessGrant grant = new AccessGrant(null, "grantee1", user);
            accessGrantRepository.save(grant);
            log.info("Default data seeded successfully");
        }
        */
    }

    @Override
    public List<AccessGrant> getAccessGrants(String username) {
        log.info("Fetching access grants for user: {}", username);

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            log.error("User not found: {}", username);
            throw new PrivacyServiceException("User not found: " + username);
        }
        User user = userOptional.get();

        List<AccessGrant> grants = accessGrantRepository.findByUser(user);
        log.info("Found {} access grants for user: {}", grants.size(), username);
        return grants;
    }

    @Override
    public void revokeAccess(String username, String granteeName) {
        log.info("Attempting to revoke access for grantee: {} from user: {}", granteeName, username);

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            log.error("User not found: {}", username);
            throw new PrivacyServiceException("User not found: " + username);
        }
        User user = userOptional.get();

        Optional<AccessGrant> grantOptional = accessGrantRepository.findByUserAndGranteeName(user, granteeName);
        if (!grantOptional.isPresent()) {
            log.error("Access grant not found for grantee: {} and user: {}", granteeName, username);
            throw new PrivacyServiceException("Access grant not found for grantee: " + granteeName);
        }
        AccessGrant toRevoke = grantOptional.get();

        accessGrantRepository.delete(toRevoke);
        log.info("Successfully revoked access for grantee: {} from user: {}", granteeName, username);
    }

    // Ngoại lệ tùy chỉnh
    public static class PrivacyServiceException extends RuntimeException {
        public PrivacyServiceException(String message) {
            super(message);
        }
    }
}