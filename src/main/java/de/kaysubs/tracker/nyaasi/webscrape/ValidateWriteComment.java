package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import org.jsoup.nodes.Document;

public class ValidateWriteComment implements Parser<Void> {
    @Override
    public Void parsePage(Document page, boolean isSukebei) {
        boolean didSucceed = page.select("div.container")
                .select("div.alert-success").stream()
                .flatMap(div -> div.textNodes().stream())
                .anyMatch(n -> n.text().trim().equals("Comment successfully posted."));

        if(didSucceed)return null;
        else throw new WebScrapeException("Unknown response");
    }
}
