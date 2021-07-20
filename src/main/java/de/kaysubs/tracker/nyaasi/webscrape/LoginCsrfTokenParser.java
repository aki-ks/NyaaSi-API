package de.kaysubs.tracker.nyaasi.webscrape;

import org.jsoup.nodes.Document;

/**
 * @deprecated CSRF tokens are no longer used on nyaa.si/sukebei.nyaa.si.
 */
@Deprecated
public class LoginCsrfTokenParser implements Parser<String> {
    @Override
    public String parsePage(Document page, boolean isSukebei) {
        return ParseUtils.getCsrfToken(page);
    }
}
