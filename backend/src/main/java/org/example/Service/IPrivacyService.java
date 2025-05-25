package org.example.Service;
import org.example.Entity.AccessGrant;

import java.util.List;

public interface IPrivacyService {
    List<AccessGrant> getAccessGrants(String username);
    void revokeAccess(String username, String granteeName);
}
