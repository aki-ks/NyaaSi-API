package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteCommentResponseParser implements Parser<Integer> {
    private final static Pattern REDIRECT_URL_PATTERN =
            Pattern.compile("https?://(?:sukebei\\.)?nyaa.si/view/(?:[0-9]+)#(.+)");

    private final String commentDivId;

    public WriteCommentResponseParser(String redirectUrl) {
        Matcher matcher = REDIRECT_URL_PATTERN.matcher(redirectUrl);
        if(matcher.matches()) {
            commentDivId = matcher.group(1);
        } else {
            throw new WebScrapeException("Cannot parse redirectUrl");
        }
    }

    @Override
    public Integer parsePage(Document page, boolean isSukebei) {
        boolean didSucceed = page.select("div.container")
                .select("div.alert-success").stream()
                .flatMap(div -> div.textNodes().stream())
                .anyMatch(n -> n.text().trim().equals("Comment successfully posted."));

        if(didSucceed) {
            return TorrentInfoParser.parseCommentDivId(page
                    .selectFirst("#" + commentDivId)
                    .selectFirst("div.comment-content")
                    .attr("id"));
        } else {
            throw new WebScrapeException("Unknown response");
        }
    }
}
