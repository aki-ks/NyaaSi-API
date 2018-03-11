package de.kaysubs.tracker.nyaasi.webscrape;

import de.kaysubs.tracker.nyaasi.model.SubCategory;
import de.kaysubs.tracker.nyaasi.exception.WebScrapeException;
import de.kaysubs.tracker.nyaasi.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

public class TorrentInfoParser implements Parser<TorrentInfo> {

    @Override
    public TorrentInfo parsePage(Document page, boolean isSukebei) {
        TorrentInfo info = new TorrentInfo();

        parseMainPanel(page, info, isSukebei);
        parseFileList(page, info);
        parseDescription(page, info);
        parseComments(page, info);

        return info;
    }

    private void parseMainPanel(Document page, TorrentInfo info, boolean isSukebei) {
        // select first/only panel with footer
        Element panel = page.select("div.panel").stream()
                .filter(e -> e.selectFirst("div.panel-footer.clearfix") != null)
                .findFirst().get();

        TorrentState torrentState;
        if(panel.is(".panel-danger"))
            torrentState = TorrentState.REMAKE;
        else if(panel.is(".panel-success"))
            torrentState = TorrentState.TRUSTED;
        else
            torrentState = TorrentState.NORMAL;

        String title = panel.selectFirst("div.panel-heading")
                .selectFirst(".panel-title")
                .text();

        Element body = panel.selectFirst("div.panel-body");
        SubCategory category = ParseUtils.parseSubCategory(
                getCell(body, 0, 0)
                .select("a").get(1)
                .attr("href"),
                true, isSukebei);

        Optional<String> uploader = Optional.ofNullable(getCell(body, 1, 0).selectFirst("a")).map(Element::text);
        String information = getCell(body, 2, 0).text();
        DataSize size = ParseUtils.parseDataSize(getCell(body, 3, 0).text());

        Date date = ParseUtils.parseTimeStamp(getCell(body, 0, 1).attr("data-timestamp"));
        int seeders = Integer.parseInt(getCell(body, 1, 1).selectFirst("span").text());
        int leechers = Integer.parseInt(getCell(body, 2, 1).selectFirst("span").text());
        int completed = Integer.parseInt(
                getCell(body, 3, 1).text());
        String hash = getCell(body, 4, 0).selectFirst("kbd").text();

        Element footer = panel.selectFirst("div.panel-footer.clearfix");
        String downloadLink = footer.select("a[href^=/download/]").attr("href");
        String magnetLink = footer.select("a.card-footer-item").attr("href");

        info.setTitle(title);
        info.setCategory(category);
        info.setUploader(uploader);
        info.setTorrentState(torrentState);
        info.setInformation(information);
        info.setSize(size);
        info.setDate(date);
        info.setSeeders(seeders);
        info.setLeechers(leechers);
        info.setCompleted(completed);
        info.setHash(hash);

        try {
            URL baseUrl = new URL(isSukebei ? "https://sukebei.nyaa.si/" : "https://nyaa.si/");
            info.setDownloadLink(new URL(baseUrl, downloadLink));
            info.setMagnetLink(new URI(magnetLink));
        } catch (MalformedURLException e) {
            throw new WebScrapeException("Cannot parse download url");
        } catch (URISyntaxException e) {
            throw new WebScrapeException("Cannot parse magnet uri");
        }
    }

    private Element getCell(Element panelBody, int row, int col) {
        return panelBody
                .select("div.row").get(row)
                .select("div.col-md-5").get(col);
    }

    private void parseComments(Document page, TorrentInfo info) {
        info.setComments(page.selectFirst("div#comments")
                .select("div.comment-panel").stream()
                .map(this::parseComment)
                .toArray(TorrentInfo.Comment[]::new));
    }

    private TorrentInfo.Comment parseComment(Element commentPanel) {
        Element userLink = commentPanel.selectFirst("a[href^=/user/]");

        String username = userLink.text();
        boolean isTrusted = userLink.attr("title").equals("Trusted");
//        boolean isUploader = userLink.siblingNodes().stream().anyMatch(node -> node instanceof TextNode &&
//                ((TextNode) node).text().trim().equals("(uploader)"));
        String avatar = commentPanel.selectFirst("img.avatar").attr("src");
        Date date = ParseUtils.parseTimeStamp(commentPanel.selectFirst("small[data-timestamp]")
                .attr("data-timestamp"));

        Element commentDiv = commentPanel.selectFirst("div.comment-content");

        return new TorrentInfo.Comment(username, isTrusted, avatar, date, commentDiv);
    }

    private void parseFileList(Document page, TorrentInfo info) {
        Element node = page.selectFirst("div.torrent-file-list > ul > li");
        info.setFile(parseFileNode(node));
    }

    private TorrentInfo.FileNode parseFileNode(Element li) {
        Element folderLink = li.selectFirst("a.folder");

        if(folderLink == null) {
            String name = li.textNodes().get(0).text().trim();

            String sizeText = li.selectFirst("span.file-size").text();
            sizeText = sizeText.substring(1, sizeText.length() - 1); // trim brackets
            DataSize size = ParseUtils.parseDataSize(sizeText);

            return new TorrentInfo.File(name, size);
        } else {
            String name = folderLink.text();
            TorrentInfo.FileNode[] children = Optional.ofNullable(li.selectFirst("ul"))
                    .map(e -> e.children().stream()
                            .map(this::parseFileNode)
                            .toArray(TorrentInfo.FileNode[]::new))
                    .orElse(new TorrentInfo.FileNode[0]);

            return new TorrentInfo.Folder(name, children);
        }
    }

    private void parseDescription(Document page, TorrentInfo info) {
        Element descriptionDiv = page.selectFirst("div#torrent-description");
        info.setDescriptionDiv(descriptionDiv);
    }

}
