package de.kaysubs.tracker.nyaasi;

import de.kaysubs.tracker.common.exception.HttpException;
import de.kaysubs.tracker.nyaasi.exception.IllegalCategoryException;
import de.kaysubs.tracker.nyaasi.exception.LoginException;
import de.kaysubs.tracker.nyaasi.exception.NoSuchTorrentException;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import de.kaysubs.tracker.nyaasi.model.SearchRequest;
import de.kaysubs.tracker.nyaasi.model.TorrentInfo;
import de.kaysubs.tracker.nyaasi.model.TorrentPreview;

public interface NyaaSiApi {
    /**
     * API for https://sukebei.nyaa.si/
     */
    static NyaaSiApi getSukebei() {
        return NyaaSiApiImpl.getSukebeiInstance();
    }

    /**
     * API for http://nyaa.si/
     */
    static NyaaSiApi getNyaa() {
        return NyaaSiApiImpl.getNyaaInstance();
    }

    boolean isSukebei();

    /**
     * Search for torrents
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws IllegalCategoryException cannot use sukebei categories on nyaa and the other way round
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentPreview[] search(SearchRequest request);

    /**
     * Get informations about a torrent
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws NoSuchTorrentException torrent id does not exist
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentInfo getTorrentInfo(int torrentId);

    /**
     * Login with session cookie.
     *
     * This is a replacement for the username/password login function.
     *
     * @param sessionCookie The session cookie from a logged in browser.
     */
    NyaaSiAuthApi login(String sessionCookie);

    /**
     * Login with username and password.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @deprecated
     * No longer works due to Google Captcha. Set session cookie manually by logging
     * in through the browser and retrieving it from cookies in developer tools.
     * Use {@link #login(String)} instead.
     *
     * @throws LoginException login failed
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    @Deprecated
    NyaaSiAuthApi login(String username, String password);

}
