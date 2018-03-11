package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.LoginException;
import de.kaysubs.tracker.nyaasi.exception.PasswordLengthException;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ValidatePasswordChange implements Parser<Void> {
    @Override
    public Void parsePage(Document page, boolean isSukebei) {
        Element helpBlock = page.selectFirst("div.help-block");
        if(helpBlock != null) {
            if(helpBlock.text().startsWith("Password must be at least")) {
                throw new PasswordLengthException();
            } else {
                throw new WebScrapeException(helpBlock.text().trim());
            }
        }

        boolean wrongPassword = page.select("div.container")
                .select("div.alert-danger").stream()
                .anyMatch(e -> e.text().trim().equals("Incorrect password."));

        boolean didSucceed = page.select("div.container")
                .select("div.alert-success > strong").stream()
                .anyMatch(strong -> strong.text().equals("Password successfully changed!"));

        if(wrongPassword)throw new LoginException();
        else if(didSucceed)return null;
        else throw new WebScrapeException("Unknown response");
    }
}
