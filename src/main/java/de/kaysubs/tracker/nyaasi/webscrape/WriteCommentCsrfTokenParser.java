package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.CaptchaException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @deprecated CSRF tokens are no longer used on nyaa.si/sukebei.nyaa.si.
 */
@Deprecated
public class WriteCommentCsrfTokenParser implements Parser<String> {
    @Override
    public String parsePage(Document page, boolean isSukebei) {
        Element form = page.selectFirst("form.comment-box");

        if(form.selectFirst("div.g-recaptcha") == null) {
            return ParseUtils.getCsrfToken(form);
        } else {
            throw new CaptchaException();
        }
    }
}
