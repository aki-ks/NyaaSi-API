package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.MissingTrackerException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUploadResponse implements Parser<Void> {
    private final Pattern TRACKER_ERROR_PATTERN = Pattern.compile("\\s*Please include (http://(nyaa|sukebei).tracker.wf:(7777|8888)/announce) in the trackers of the torrent\\s*");

    @Override
    public Void parsePage(Document page, boolean isSukebei) {
        for(Element helpBlock : page.select("div.help-block")) {
            Matcher matcher = TRACKER_ERROR_PATTERN.matcher(helpBlock.text());

            if(matcher.matches())
                throw new MissingTrackerException(matcher.group(1));
        }

        return null;
    }
}
