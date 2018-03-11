package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import de.kaysubs.tracker.nyaasi.model.DataSize;
import de.kaysubs.tracker.nyaasi.model.SubCategory;
import de.kaysubs.tracker.nyaasi.model.TorrentPreview;
import de.kaysubs.tracker.nyaasi.model.TorrentState;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TorrentListPage implements Parser<TorrentPreview[]> {

    @Override
    public TorrentPreview[] parsePage(Document page, boolean isSukebei) {
        return page.selectFirst("table.torrent-list")
                .selectFirst("tbody")
                .select("tr").stream()
                .map(e -> parseTorrent(e, isSukebei))
                .toArray(TorrentPreview[]::new);
    }

    private TorrentPreview parseTorrent(Element row, boolean isSukebei) {
        TorrentState torrentState;
        if(row.is(".danger"))
            torrentState = TorrentState.REMAKE;
        else if(row.is(".success"))
            torrentState = TorrentState.TRUSTED;
        else
            torrentState = TorrentState.NORMAL;

        Element[] cells = row.select("td").toArray(new Element[0]);

        SubCategory category = ParseUtils.parseSubCategory(cells[0].select("a").attr("href"),true, isSukebei);

        Element titleCell = cells[1];
        Element titleLink = titleCell.selectFirst("a:not(.comments)");
        String title = titleLink.text();
        int torrentId = parseViewUrl(titleLink.attr("href"));

        int commentCount = Optional.ofNullable(titleCell.selectFirst("a.comments"))
                .map(e -> Integer.parseInt(e.text()))
                .orElse(0);

        URL downloadLink;
        URI magnetLink;
        Elements links = cells[2].select("a");
        try {
            URL baseUrl = new URL(isSukebei ? "https://sukebei.nyaa.si" : "https://nyaa.si");
            downloadLink = new URL(baseUrl, links.get(0).attr("href"));

            magnetLink = new URI(links.get(1).attr("href"));
        } catch (MalformedURLException e) {
            throw new WebScrapeException("Cannot parse download url");
        } catch (URISyntaxException e) {
            throw new WebScrapeException("Cannot parse magnet uri");
        }

        DataSize size = ParseUtils.parseDataSize(cells[3].text());
        Date date = ParseUtils.parseTimeStamp(cells[4].attr("data-timestamp"));
        int seeders = Integer.parseInt(cells[5].text());
        int leechers = Integer.parseInt(cells[6].text());
        int completed = Integer.parseInt(cells[7].text());

        return new TorrentPreview(torrentId, torrentState, category, title, commentCount, downloadLink, magnetLink, size, date, seeders, leechers, completed);
    }

    Pattern VIEW_URL_PATTERN = Pattern.compile("/view/([0-9]+)");
    private int parseViewUrl(String viewUrl) {
        Matcher matcher = VIEW_URL_PATTERN.matcher(viewUrl);
        if(matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new WebScrapeException("Cannot parse view url");
        }
    }
}
