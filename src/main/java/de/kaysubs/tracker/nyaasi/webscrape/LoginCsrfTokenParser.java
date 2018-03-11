package de.kaysubs.tracker.nyaasi.webscrape;

import org.jsoup.nodes.Document;

public class LoginCsrfTokenParser implements Parser<String> {
    @Override
    public String parsePage(Document page, boolean isSukebei) {
        return ParseUtils.getCsrfToken(page);
    }
}
