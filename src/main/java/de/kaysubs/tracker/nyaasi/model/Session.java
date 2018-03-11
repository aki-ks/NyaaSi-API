package de.kaysubs.tracker.nyaasi.model;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class Session {

    private final String sessionId;
    private final boolean isSubekei;

    public Session(String sessionId, boolean isSubekei) {
        this.sessionId = sessionId;
        this.isSubekei = isSubekei;
    }

    public boolean isSubekei() {
        return isSubekei;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Cookie toCookie() {
        BasicClientCookie cookie = new BasicClientCookie("session", sessionId);
        cookie.setPath("/");
        cookie.setDomain(isSubekei ? "sukebei.nyaa.si" : "nyaa.si");
        return cookie;
    }
}
