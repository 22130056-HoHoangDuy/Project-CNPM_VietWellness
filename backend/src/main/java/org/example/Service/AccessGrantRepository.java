package org.example.Service;

import org.example.Entity.AccessGrant;
import org.example.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessGrantRepository extends JpaRepository<AccessGrant, Long> {
    List<AccessGrant> findByUser(User user);
}