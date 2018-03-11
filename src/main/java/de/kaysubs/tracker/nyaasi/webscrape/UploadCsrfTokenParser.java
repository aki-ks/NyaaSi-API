package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.CaptchaException;
import org.jsoup.nodes.Document;

public class UploadCsrfTokenParser implements Parser<String> {
    @Override
    public String parsePage(Document page, boolean isSukebei) {
        if(page.selectFirst("div.g-recaptcha") != null) {
            throw new CaptchaException();
        }

        return ParseUtils.getCsrfToken(page);
    }
}
