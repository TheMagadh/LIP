package Fectum.co.in.LPI.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    // Find API key by the key string
    Optional<ApiKey> findByApiKey(String apiKey);

    // Custom queries for filtering
    List<ApiKey> findByUsername(String username);

    List<ApiKey> findByPurpose(String purpose);

    List<ApiKey> findByIpAddress(String ipAddress);

    List<ApiKey> findByUsernameAndPurpose(String username, String purpose);
}
