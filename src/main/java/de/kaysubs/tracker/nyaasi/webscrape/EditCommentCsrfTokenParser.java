package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.CannotEditException;
import de.kaysubs.tracker.nyaasi.exception.NoSuchCommentException;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EditCommentCsrfTokenParser implements Parser<String> {
    private final int commentId;

    public EditCommentCsrfTokenParser(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public String parsePage(Document page, boolean isSukebei) {
        Element commentDiv = page.select("div#comments")
                .select("div.comment-panel").stream()
                .filter(e -> e.selectFirst("div#torrent-comment" + commentId) != null).findFirst()
                .orElseThrow(() -> new NoSuchCommentException());

        if(commentDiv.selectFirst("button[title=Edit]") == null) {
            throw new CannotEditException();
        }

        Elements crsfToken = commentDiv
                .select(".edit-comment-box")
                .select("input#csrf_token");

        if(crsfToken.hasAttr("value")) {
            return crsfToken.attr("value");
        } else {
            throw new WebScrapeException("Cannot extract CSRF token");
        }
    }
}
