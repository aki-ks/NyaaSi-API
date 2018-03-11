package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import de.kaysubs.tracker.nyaasi.model.AccountInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountInfoParser implements Parser<AccountInfo> {
    private final SimpleDateFormat REGISTRATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public AccountInfo parsePage(Document page, boolean isSukebei) {
        String username = page.selectFirst("h2 > strong.text-default").text();
        String avatarUrl = page.selectFirst("img.avatar").attr("src");
        int userId = Integer.parseInt(getAfterDt(page, "User ID:").text());
        String userClass = getAfterDt(page, "User Class:").text();
        Date registrationDate = parseRegistrationDate(getAfterDt(page, "User Created on:").text());
        String currentEmail = page.selectFirst("div#email-change")
                .selectFirst("label[for=current_email] + div").text();

        return new AccountInfo(username, userId, registrationDate, avatarUrl, userClass, currentEmail);
    }

    private Date parseRegistrationDate(String dateString) {
        try {
            return REGISTRATION_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new WebScrapeException("Cannot parser registration date");
        }
    }

    private Element getAfterDt(Document page, String textOfDt) {
        return page.select("dt").stream()
                .filter(e -> e.text().equals(textOfDt)).findAny()
                .orElseThrow(() -> new WebScrapeException("Cannot find <dt>" + textOfDt + "</dt>"))
                .nextElementSibling();
    }
}
