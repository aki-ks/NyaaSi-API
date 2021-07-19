package de.kaysubs.tracker.nyaasi;

import de.kaysubs.tracker.common.HttpUtil;
import de.kaysubs.tracker.common.exception.HttpErrorCodeException;
import de.kaysubs.tracker.common.exception.HttpException;
import de.kaysubs.tracker.nyaasi.exception.*;
import de.kaysubs.tracker.nyaasi.model.SearchRequest;
import de.kaysubs.tracker.nyaasi.model.Session;
import de.kaysubs.tracker.nyaasi.model.TorrentInfo;
import de.kaysubs.tracker.nyaasi.model.TorrentPreview;
import de.kaysubs.tracker.nyaasi.webscrape.*;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NyaaSiApiImpl implements NyaaSiApi {
    private final static NyaaSiApiImpl SUKEBEI_INSTANCE = new NyaaSiApiImpl(true);
    private final static NyaaSiApiImpl NYAA_INSTANCE = new NyaaSiApiImpl(false);

    public static NyaaSiApiImpl getSukebeiInstance() {
        return SUKEBEI_INSTANCE;
    }

    public static NyaaSiApiImpl getNyaaInstance() {
        return NYAA_INSTANCE;
    }

    protected final boolean isSukebei;
    protected final String domain;

    public NyaaSiApiImpl(boolean isSukebei) {
        this.isSukebei = isSukebei;
        this.domain = isSukebei ? "sukebei.nyaa.si" : "nyaa.si";
    }

    @Override
    public boolean isSukebei() {
        return isSukebei;
    }

    protected <T> T parsePage(HttpResponse response, Parser<T> parser) {
        Document page = Jsoup.parse(HttpUtil.readIntoString(response));

        try {
            return parser.parsePage(page, isSukebei);
        } catch(NyaaSiException | HttpException e) {
            throw e;
        } catch(Exception e) {
            throw new WebScrapeException(e);
        }
    }

    @Override
    public TorrentPreview[] search(SearchRequest request) {
        URI uri;
        try {
            URIBuilder builder = new URIBuilder()
                    .setScheme("https")
                    .setHost(domain);

            request.getTerm().ifPresent(term ->
                    builder.addParameter("q", term));

            request.getCategory().ifPresent(category -> {
                if(category.isSukebei() != isSukebei)
                    throw new IllegalCategoryException();

                builder.addParameter("c", category.getMainCategoryId() + "_" + category.getSubCategoryId());
            });

            request.getFilter().ifPresent(filter ->
                    builder.addParameter("f", Integer.toString(filter.getId())));

            request.getUser().ifPresent(user ->
                    builder.addParameter("u", user));

            request.getPage().ifPresent(page ->
                    builder.addParameter("p", Integer.toString(page)));

            request.getOrdering().ifPresent(ordering ->
                    builder.addParameter("o", ordering.getId()));

            request.getSortedBy().ifPresent(sort ->
                    builder.addParameter("s", sort.getId()));

            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new HttpException("Cannot build URL", e);
        }

        HttpGet get = new HttpGet(uri);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get);
        int statusCode = response.getStatusLine().getStatusCode();
        switch(statusCode) {
            case 404: return new TorrentPreview[0];
            case 200: return parsePage(response, new TorrentListPage());
            default: throw new HttpErrorCodeException(statusCode);
        }
    }

    @Override
    public TorrentInfo getTorrentInfo(int torrentId) {
        HttpGet get = new HttpGet("https://" + domain + "/view/" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get);

        if(response.getStatusLine().getStatusCode() == 404)
            throw new NoSuchTorrentException(torrentId);

        return parsePage(response, new TorrentInfoParser());
    }

    @Override
    public NyaaSiAuthApi login(String sessionCookie) {
        Session session = new Session(sessionCookie, isSukebei);
        return new NyaaSiAuthApiImpl(session);
    }

    /**
     * @deprecated No longer works due to Google Captcha. Set session cookie manually
     * by logging in through the browser and retrieving it from cookies in developer
     * tools.
     */
    @Override
    @Deprecated
    public NyaaSiAuthApi login(String username, String password) {
        CookieStore store = new BasicCookieStore();
        HttpPost post = new HttpPost("https://" + domain + "/login");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("username", username));
        form.add(new BasicNameValuePair("password", password));
        post.setEntity(new UrlEncodedFormEntity(form, Consts.UTF_8));

        HttpResponse response = HttpUtil.executeRequest(post, store);
        boolean didFail = Arrays.stream(response.getHeaders("Location"))
                .anyMatch(e -> e.getValue().endsWith("/login"));

        if(didFail) {
            throw new LoginException();
        } else {
            Session session = sessionFromCookies(store.getCookies());
            return new NyaaSiAuthApiImpl(session);
        }
    }

    private Session sessionFromCookies(List<Cookie> cookies) {
        String sessionId = cookies.stream()
                .filter(c -> c.getName().equals("session")).findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new HttpException("Server did not respond with a session cookie"));

        return new Session(sessionId, isSukebei);
    }

}
