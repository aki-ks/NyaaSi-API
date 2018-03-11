package de.kaysubs.tracker.nyaasi.model;

import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

public class TorrentInfo {
    private String title;
    private Element descriptionDiv;
    private SubCategory category;
    private DataSize size;
    private Date date;
    private Optional<String> uploader;
    private TorrentState torrentState;
    private int seeders;
    private int leechers;
    private int completed;
    private String information;
    private String hash;
    private URL downloadLink;
    private URI magnetLink;
    private FileNode file;
    private Comment[] comments;

    public static class Comment {
        private final int commentId;
        private final String username;
        private final boolean isTrusted;
        private final String avatar;
        private final Date date;
        private final Element commentDiv;

        public Comment(int commentId, String username, boolean isTrusted, String avatar, Date date, Element commentDiv) {
            this.commentId = commentId;
            this.username = username;
            this.isTrusted = isTrusted;
            this.avatar = avatar;
            this.date = date;
            this.commentDiv = commentDiv;
        }

        public int getCommentId() {
            return commentId;
        }

        public String getUsername() {
            return username;
        }

        public boolean isTrusted() {
            return isTrusted;
        }

        public String getAvatar() {
            return avatar;
        }

        public Date getDate() {
            return date;
        }

        public String getComment() {
            return getCommentDiv().text();
        }

        public Element getCommentDiv() {
            return commentDiv;
        }
    }

    public interface FileNode {
        String getName();
    }

    public static class File implements FileNode {
        private final String name;
        private final DataSize size;

        public File(String name, DataSize size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public DataSize getSize() {
            return size;
        }
    }

    public static class Folder implements FileNode {
        private final String name;
        private final FileNode[] children;

        public Folder(String name, FileNode[] children) {
            this.name = name;
            this.children = children;
        }

        public String getName() {
            return name;
        }

        public FileNode[] getChildren() {
            return children;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return getDescriptionDiv().text();
    }

    public Element getDescriptionDiv() {
        return descriptionDiv;
    }

    public void setDescriptionDiv(Element descriptionDiv) {
        this.descriptionDiv = descriptionDiv;
    }

    public SubCategory getCategory() {
        return category;
    }

    public void setCategory(SubCategory category) {
        this.category = category;
    }

    public DataSize getSize() {
        return size;
    }

    public void setSize(DataSize size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Optional<String> getUploader() {
        return uploader;
    }

    public void setUploader(Optional<String> uploader) {
        this.uploader = uploader;
    }

    public TorrentState getTorrentState() {
        return torrentState;
    }

    public TorrentInfo setTorrentState(TorrentState torrentState) {
        this.torrentState = torrentState;
        return this;
    }

    public int getSeeders() {
        return seeders;
    }

    public void setSeeders(int seeders) {
        this.seeders = seeders;
    }

    public int getLeechers() {
        return leechers;
    }

    public void setLeechers(int leechers) {
        this.leechers = leechers;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public URL getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(URL downloadLink) {
        this.downloadLink = downloadLink;
    }

    public URI getMagnetLink() {
        return magnetLink;
    }

    public void setMagnetLink(URI magnetLink) {
        this.magnetLink = magnetLink;
    }

    public FileNode getFile() {
        return file;
    }

    public void setFile(FileNode file) {
        this.file = file;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }
}
