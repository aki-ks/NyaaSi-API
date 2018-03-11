package de.kaysubs.tracker.nyaasi;

import de.kaysubs.tracker.common.exception.HttpException;
import de.kaysubs.tracker.nyaasi.exception.*;
import de.kaysubs.tracker.nyaasi.model.AccountInfo;
import de.kaysubs.tracker.nyaasi.model.EditTorrentRequest;
import de.kaysubs.tracker.nyaasi.model.UploadTorrentRequest;

import java.util.function.Consumer;

/**
 * API calls that require authentication.
 */
public interface NyaaSiAuthApi extends NyaaSiApi {

    /**
     * Get information about your account.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    AccountInfo getAccountInfo();

    /**
     * Change your email address
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws LoginException wrong password
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void changeEmail(String currentPassword, String newEmail);

    /**
     * Change your password.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws LoginException wrong password
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void changePassword(String currentPassword, String newPassword);

    /**
     * Upload a torrent.
     *
     * If you've just registered at nyaa.si,
     * you must solve a captcha and cannot use this api yet.
     * After ~1 week the captcha should disappear.
     *
     * The seedfile must contain the tracker
     * http://nyaa.tracker.wf:7777/announce when uploading to nyaa or
     * http://sukebei.tracker.wf:8888/announce when uploading to sukebei.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @return torrent id
     *
     * @throws CaptchaException you must solve a captcha before upload
     * @throws MissingTrackerException torrent does not contain the required tracker
     * @throws IllegalCategoryException cannot use sukebei categories on nyaa and the other way round
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    int uploadTorrent(UploadTorrentRequest request);

    /**
     * Delete a torrent uploaded with this account.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws PermissionException you are not allowed to delete this torrent
     * @throws NoSuchTorrentException the torrent does not exist
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void deleteTorrent(int torrentId);

    /**
     * Edit a torrent uploaded with this account.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws IllegalCategoryException cannot use sukebei categories on nyaa and the other way round
     * @throws PermissionException you are not allowed to edit this torrent
     * @throws NoSuchTorrentException the torrent does not exist
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void editTorrent(int torrentId, Consumer<EditTorrentRequest> f);

    /**
     * Post a comment below a torrent.
     *
     * If you've just registered at nyaa.si you must solve a captcha
     * before writing comments and cannot use this api call yet.
     * After ~1 week the captcha should disappear.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @return comment id
     * @throws CaptchaException you must solve a captcha
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    int writeComment(int torrentId, String message);

}
