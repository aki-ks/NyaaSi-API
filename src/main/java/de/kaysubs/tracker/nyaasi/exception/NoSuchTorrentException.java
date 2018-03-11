package de.kaysubs.tracker.nyaasi.exception;

public class NoSuchTorrentException extends NyaaSiException {
    private final int torrentId;

    public NoSuchTorrentException(int torrentId) {
        super(Integer.toString(torrentId));
        this.torrentId = torrentId;
    }

    public int getTorrentId() {
        return torrentId;
    }

}
