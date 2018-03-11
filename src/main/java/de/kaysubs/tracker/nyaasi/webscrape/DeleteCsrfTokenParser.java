package de.kaysubs.tracker.nyaasi.webscrape;

import org.jsoup.nodes.Document;

public class DeleteCsrfTokenParser implements Parser<String> {
    @Override
    public String parsePage(Document page, boolean isSukebei) {
        return ParseUtils.getCsrfToken(page.selectFirst("div.panel-danger"));
    }
}
