package Fectum.co.in.LPI.Security;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey generateAndSaveApiKey(String username, String purpose, String comments, String ipAddress) {
        String generatedApiKey = UUID.randomUUID().toString();
        ApiKey apiKey = new ApiKey(username, generatedApiKey, purpose, comments, ipAddress);
        return apiKeyRepository.save(apiKey);
    }

    public boolean validateApiKey(String apiKey) {
        return apiKeyRepository.findByApiKey(apiKey).isPresent();
    }

    public List<ApiKey> filterApiKeys(Optional<String> username, Optional<String> purpose, Optional<String> ipAddress) {
        if (username.isPresent() && purpose.isPresent()) {
            return apiKeyRepository.findByUsernameAndPurpose(username.get(), purpose.get());
        } else if (username.isPresent()) {
            return apiKeyRepository.findByUsername(username.get());
        } else if (purpose.isPresent()) {
            return apiKeyRepository.findByPurpose(purpose.get());
        } else if (ipAddress.isPresent()) {
            return apiKeyRepository.findByIpAddress(ipAddress.get());
        } else {
            return apiKeyRepository.findAll();
        }
    }
}
