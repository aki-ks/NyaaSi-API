package de.kaysubs.tracker.nyaasi;

import de.kaysubs.tracker.common.HttpUtil;
import de.kaysubs.tracker.common.exception.HttpErrorCodeException;
import de.kaysubs.tracker.nyaasi.exception.*;
import de.kaysubs.tracker.nyaasi.model.*;
import de.kaysubs.tracker.nyaasi.webscrape.*;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NyaaSiAuthApiImpl extends NyaaSiApiImpl implements NyaaSiAuthApi {
    private final static Pattern VIEW_URL_PATTERN = Pattern.compile("https?://(?:sukebei\\.)?nyaa.si/view/([0-9]+)");

    private final Session session;

    public NyaaSiAuthApiImpl(Session session, boolean isSukebei) {
        super(isSukebei);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    private HttpResponse fetchAccountInfoPage() {
        return fetchAccountInfoPage(new Cookie[] { session.toCookie()});
    }

    private HttpResponse fetchAccountInfoPage(Cookie[] cookies) {
        HttpGet get = new HttpGet("https://" + domain + "/profile");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return HttpUtil.executeRequest(get, cookies);
    }

    @Override
    public AccountInfo getAccountInfo() {
        HttpResponse response = fetchAccountInfoPage();
        return parsePage(response, new AccountInfoParser());
    }

    @Override
    public void changeEmail(String currentPassword, String newEmail) {
        String csrfToken = parsePage(fetchAccountInfoPage(), new AccountInfoCsrfTokenParser()).getEmailToken();

        HttpPost post = new HttpPost("https://" + domain + "/profile");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("csrf_token", csrfToken));
        form.add(new BasicNameValuePair("email", newEmail));
        form.add(new BasicNameValuePair("current_password", currentPassword));
        post.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(session.toCookie());
        HttpUtil.executeRequest(post, cookieStore);

        HttpResponse response = fetchAccountInfoPage(cookieStore.getCookies().toArray(new Cookie[0]));
        parsePage(response, new ValidateEmailChange());
    }

    @Override
    public void changePassword(String currentPassword, String newPassword) {
        if(currentPassword.isEmpty())
            throw new LoginException();

        String csrfToken = parsePage(fetchAccountInfoPage(), new AccountInfoCsrfTokenParser()).getPasswordToken();
        HttpPost post = new HttpPost("https://" + domain + "/profile");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("csrf_token", csrfToken));
        form.add(new BasicNameValuePair("current_password", currentPassword));
        form.add(new BasicNameValuePair("new_password", newPassword));
        form.add(new BasicNameValuePair("password_confirm", newPassword));
        post.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(session.toCookie());
        HttpResponse response = HttpUtil.executeRequest(post, cookieStore);

        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 302) {
            response = fetchAccountInfoPage(cookieStore.getCookies().toArray(new Cookie[0]));
            parsePage(response, new ValidatePasswordChange());
        } else if(statusCode != 200) {
            throw new HttpErrorCodeException(statusCode);
        }

        parsePage(response, new ValidatePasswordChange());
    }

    private String newUploadCsrfToken() {
        HttpGet get = new HttpGet("https://" + domain + "/upload");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });
        return parsePage(response, new UploadCsrfTokenParser());
    }

    @Override
    public int uploadTorrent(UploadTorrentRequest request) {
        HttpPost post = new HttpPost("https://" + domain + "/upload");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("csrf_token", newUploadCsrfToken());

        ContentType torrentMime = ContentType.create("application/x-bittorrent");
        builder.addBinaryBody("torrent_file", request.getSeedfile(), torrentMime, request.getSeedfile().getName());

        builder.addTextBody("display_name", request.getName());

        SubCategory c = request.getCategory();
        if(c.isSukebei() != isSukebei)
            throw new IllegalCategoryException();
        builder.addTextBody("category", c.getMainCategoryId() + "_" + c.getSubCategoryId());

        builder.addTextBody("information", request.getInformation().orElse(""));

        if(request.isAnonymous())
            builder.addTextBody("is_anonymous", "y");

        if(request.isHidden())
            builder.addTextBody("is_hidden", "y");

        if(request.isRemake())
            builder.addTextBody("is_remake", "y");

        if(request.isCompleted())
            builder.addTextBody("is_complete", "y");

        builder.addTextBody("description", request.getDescription().orElse(""));

        post.setEntity(builder.build());

        HttpResponse response = HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });

        parsePage(response, new ValidateUploadResponse());

        return parseViewUrl(response.getFirstHeader("Location").getValue());
    }

    private int parseViewUrl(String viewUrl) {
        Matcher matcher = VIEW_URL_PATTERN.matcher(viewUrl);
        if(matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new WebScrapeException("Cannot parse view url");
        }
    }

    private String newDeleteCsrfToken(int torrentId) {
        HttpGet get = new HttpGet("https://" + domain + "/view/" + torrentId + "/edit");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200: return parsePage(response, new DeleteCsrfTokenParser());
            case 403: throw new PermissionException();
            case 404: throw new NoSuchTorrentException(torrentId);
            default: throw new HttpErrorCodeException(statusCode);
        }
    }

    @Override
    public void deleteTorrent(int torrentId) {
        String csrfToken = newDeleteCsrfToken(torrentId);

        HttpPost post = new HttpPost("https://" + domain + "/view/" + torrentId + "/edit");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("csrf_token", csrfToken));
        form.add(new BasicNameValuePair("delete", "Delete"));
        post.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

        HttpUtil.executeRequest(post, new Cookie[] { session.toCookie() });
    }

    @Override
    public void editTorrent(int torrentId, Consumer<EditTorrentRequest> f) {
        EditTorrentRequest request = newEditRequest(torrentId);
        f.accept(request);
        if(request.getCategory().isSukebei() != isSukebei)
            throw new IllegalCategoryException();

        HttpPost post = new HttpPost("https://" + domain + "/view/" + torrentId + "/edit");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        SubCategory c = request.getCategory();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("csrf_token", request.getCsrfToken());
        builder.addTextBody("display_name", request.getName());
        builder.addTextBody("category", c.getMainCategoryId() + "_" + c.getSubCategoryId());
        builder.addTextBody("information", request.getInformation());
        if(request.isAnonymous())builder.addTextBody("is_anonymous", "y");
        if(request.isHidden())builder.addTextBody("is_hidden", "y");
        if(request.isRemake())builder.addTextBody("is_remake", "y");
        if(request.isCompleted())builder.addTextBody("is_complete", "y");
        builder.addTextBody("description", request.getDescription());
        builder.addTextBody("submit", "Save Changes");
        post.setEntity(builder.build());

        HttpUtil.executeRequest(post, new Cookie[] { session.toCookie()} );
    }

    private EditTorrentRequest newEditRequest(int torrentId) {
        HttpGet get = new HttpGet("https://" + domain + "/view/" + torrentId + "/edit");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, new Cookie[] { session.toCookie() });

        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200: return parsePage(response, new EditTorrentParser());
            case 403: throw new PermissionException();
            case 404: throw new NoSuchTorrentException(torrentId);
            default: throw new HttpErrorCodeException(statusCode);
        }
    }

    private HttpResponse fetchViewTorrentPage(int torrentId, CookieStore store) {
        HttpGet get = new HttpGet("https://" + domain + "/view/" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        return HttpUtil.executeRequest(get, store);
    }

    private String newWriteCommentCsrfToken(int torrentId) {
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(session.toCookie());
        HttpResponse response = fetchViewTorrentPage(torrentId, cookieStore);
        return parsePage(response, new WriteCommentCsrfTokenParser());
    }

    @Override
    public int writeComment(int torrentId, String message) {
        String csrfToken = newWriteCommentCsrfToken(torrentId);

        HttpPost post = new HttpPost("https://" + domain + "/view/" + torrentId);
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("csrf_token", csrfToken));
        form.add(new BasicNameValuePair("comment", message));
        post.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(session.toCookie());
        HttpResponse response = HttpUtil.executeRequest(post, cookieStore);

        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 302) {
            String redirectUrl = response.getFirstHeader("Location").getValue();
            response = fetchViewTorrentPage(torrentId, cookieStore);
            return parsePage(response, new WriteCommentResponseParser(redirectUrl));
        } else {
            throw new HttpErrorCodeException(statusCode);
        }
    }
}
