package Fectum.co.in.LPI.Security;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;

    @Column(name = "purpose", nullable = true)
    private String purpose;

    @Column(name = "comments", nullable = true)
    private String comments;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "api_key_policy", nullable = false)
    private String apiKeyPolicy;

    // Default constructor
    public ApiKey() {
        // Generate a random 11-digit user ID
        this.userId = generateRandomUserId();
    }

    // Constructor with fields
    public ApiKey(String username, String apiKey, String purpose, String comments, String ipAddress) {
        this.userId = generateRandomUserId();
        this.username = username;
        this.apiKey = apiKey;
        this.purpose = purpose;
        this.comments = comments;
        this.ipAddress = ipAddress;
        this.createdAt = LocalDateTime.now();
        this.apiKeyPolicy = "API keys are issued for personal or organizational use. Keep them confidential and never share them. Misuse may lead to revocation.";
    }

    // Generate random 11-digit user ID
    private String generateRandomUserId() {
        Random random = new Random();
        StringBuilder userIdBuilder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            userIdBuilder.append(random.nextInt(10));  // Random digit from 0-9
        }
        return userIdBuilder.toString();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getApiKeyPolicy() {
        return apiKeyPolicy;
    }
}

