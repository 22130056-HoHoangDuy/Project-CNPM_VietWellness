package org.example.Service;

import org.example.Entity.AccessGrant;
import org.example.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessGrantRepository extends JpaRepository<AccessGrant, Long> {
    List<AccessGrant> findByUser(User user);

    Optional<AccessGrant> findByUserAndGranteeName(User user, String granteeName);
}