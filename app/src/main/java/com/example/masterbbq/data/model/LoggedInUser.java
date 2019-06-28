package com.example.masterbbq.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String token;
    private String email;
    private boolean active;
    private boolean verify;


    public LoggedInUser(String userId, String displayName, String token, String email, boolean active, boolean verify) {
        this.userId = userId;
        this.displayName = displayName;
        this.token = token;
        this.email = email;
        this.active = active;
        this.verify = verify;
    }

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
