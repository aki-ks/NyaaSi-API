package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.LoginException;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import org.jsoup.nodes.Document;

public class ValidateEmailChange implements Parser<Void> {
    @Override
    public Void parsePage(Document page, boolean isSukebei) {
        boolean wrongPassword = page.select("div.container")
                .select("div.alert-danger").stream()
                .anyMatch(e -> e.text().trim().equals("Incorrect password."));

        boolean didSucceed = page.select("div.container")
                .select("div.alert-success > strong").stream()
                .anyMatch(strong -> strong.text().equals("Email successfully changed!"));

        if(wrongPassword)throw new LoginException();
        else if(didSucceed)return null;
        else throw new WebScrapeException("Unknown response");
    }
}
