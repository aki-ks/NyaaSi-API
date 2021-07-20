package de.kaysubs.tracker.nyaasi.model;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class Session {

    private final String sessionId;
    private final boolean isSukebei;

    public Session(String sessionId, boolean isSukebei) {
        this.sessionId = sessionId;
        this.isSukebei = isSukebei;
    }

    /**
     * @deprecated Use {@link #isSukebei} instead.
     */
    @Deprecated
    public boolean isSubekei() {
        return isSukebei;
    }

    public boolean isSukebei() {
        return isSukebei;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Cookie toCookie() {
        BasicClientCookie cookie = new BasicClientCookie("session", sessionId);
        cookie.setPath("/");
        cookie.setDomain(isSukebei ? "sukebei.nyaa.si" : "nyaa.si");
        return cookie;
    }
}
