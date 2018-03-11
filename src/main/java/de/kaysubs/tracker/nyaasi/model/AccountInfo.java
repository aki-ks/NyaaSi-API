package de.kaysubs.tracker.nyaasi.model;

import java.util.Date;

public class AccountInfo {

    private final String name;
    private final int userId;
    private final Date registrationDate;
    private final String avatarUrl;
    private final String userClass;
    private final String email;

    public AccountInfo(String name, int userId, Date registrationDate, String avatarUrl, String userClass, String email) {
        this.name = name;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.avatarUrl = avatarUrl;
        this.userClass = userClass;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUserClass() {
        return userClass;
    }

    public String getEmail() {
        return email;
    }
}
