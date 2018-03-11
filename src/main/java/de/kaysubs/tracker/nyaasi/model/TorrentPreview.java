package de.kaysubs.tracker.nyaasi.model;

import java.net.URI;
import java.net.URL;
import java.util.Date;

public class TorrentPreview {
    private final int id;
    private final TorrentState torrentState;
    private final SubCategory category;
    private final String title;
    private final int commentCount;
    private final URL downloadLink;
    private final URI magnetLink;
    private final DataSize size;
    private final Date date;
    private final int seeders;
    private final int leechers;
    private final int completed;

    public TorrentPreview(int id, TorrentState torrentState, SubCategory category, String title,
                          int commentCount, URL downloadLink, URI magnetLink, DataSize size, Date date,
                          int seeders, int leechers, int completed) {
        this.id = id;
        this.torrentState = torrentState;
        this.category = category;
        this.title = title;
        this.commentCount = commentCount;
        this.downloadLink = downloadLink;
        this.magnetLink = magnetLink;
        this.size = size;
        this.date = date;
        this.seeders = seeders;
        this.leechers = leechers;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public TorrentState getTorrentState() {
        return torrentState;
    }

    public SubCategory getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public URL getDownloadLink() {
        return downloadLink;
    }

    public URI getMagnetLink() {
        return magnetLink;
    }

    public DataSize getSize() {
        return size;
    }

    public Date getDate() {
        return date;
    }

    public int getSeeders() {
        return seeders;
    }

    public int getLeechers() {
        return leechers;
    }

    public int getCompleted() {
        return completed;
    }
}
