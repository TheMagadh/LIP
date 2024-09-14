package Fectum.co.in.LPI.Security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/generatekey")
    public ResponseEntity<ApiKey> generateApiKey(
            @RequestBody ApiKeyRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = httpRequest.getRemoteAddr();
        ApiKey apiKey = apiKeyService.generateAndSaveApiKey(request.getUsername(), request.getPurpose(), request.getComments(), ipAddress);
        return new ResponseEntity<>(apiKey, HttpStatus.CREATED);
    }

    @GetMapping("/filter-keys")
    public ResponseEntity<List<ApiKey>> filterApiKeys(
            @RequestParam Optional<String> username,
            @RequestParam Optional<String> purpose,
            @RequestParam Optional<String> ipAddress) {

        List<ApiKey> apiKeys = apiKeyService.filterApiKeys(username, purpose, ipAddress);
        return new ResponseEntity<>(apiKeys, HttpStatus.OK);
    }

    public static class ApiKeyRequest {
        private String username;
        private String purpose;
        private String comments;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }
}
