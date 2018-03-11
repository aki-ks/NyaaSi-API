package de.kaysubs.tracker.nyaasi.webscrape;

import org.jsoup.nodes.Document;

public class AccountInfoCsrfTokenParser implements Parser<AccountInfoCsrfTokenParser.Tokens> {
    @Override
    public Tokens parsePage(Document page, boolean isSukebei) {
        return new Tokens(
                ParseUtils.getCsrfToken(page.selectFirst("div#email-change")),
                ParseUtils.getCsrfToken(page.selectFirst("div#password-change")));
    }

    public static class Tokens {
        private final String emailToken;
        private final String passwordToken;

        public Tokens(String emailToken, String passwordToken) {
            this.emailToken = emailToken;
            this.passwordToken = passwordToken;
        }

        public String getEmailToken() {
            return emailToken;
        }

        public String getPasswordToken() {
            return passwordToken;
        }
    }
}
