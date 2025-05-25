package org.example.PrivacyController;
import lombok.RequiredArgsConstructor;
import org.example.DTO.response.MessageResponseDTO;
import org.example.Entity.AccessGrant;
import org.example.Entity.User;
import org.example.Service.IPrivacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.example.DTO.request.AccessRevokeRequest;
import java.util.List;

@RestController
@RequestMapping("/api/privacy")
@RequiredArgsConstructor


public class PrivacyController {
    @Autowired
    private IPrivacyService privacyService;
    // private final IPrivacyService privacyService;
    @GetMapping("/access")
    public ResponseEntity<List<AccessGrant>> getAccessGrants(@RequestAttribute("user") User user) {
        try {
            List<AccessGrant> grants = privacyService.getAccessGrants(user.getUsername());
            return ResponseEntity.ok(grants);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PostMapping("/revoke")
    public ResponseEntity<MessageResponseDTO> revokeAccess(@RequestBody AccessRevokeRequest requestDTO,
                                                           @RequestAttribute("user") User user) {
        try {
            privacyService.revokeAccess(user.getUsername(), requestDTO.getGranteeName());
            MessageResponseDTO response = new MessageResponseDTO("Đã thu hồi quyền truy cập của " + requestDTO.getGranteeName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
