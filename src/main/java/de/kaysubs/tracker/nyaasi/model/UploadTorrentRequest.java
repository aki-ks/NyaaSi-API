package de.kaysubs.tracker.nyaasi.model;

import java.io.File;
import java.util.Optional;

public class UploadTorrentRequest {
    private final File seedfile;
    private final String name;
    private final SubCategory category;
    private Optional<String> information = Optional.empty();
    private Optional<String> description = Optional.empty();
    private boolean isAnonymous = false;
    private boolean isHidden = false;
    private boolean isRemake = false;
    private boolean isCompleted = false;

    public UploadTorrentRequest(File seedfile, String name, SubCategory category) {
        this.seedfile = seedfile;
        this.name = name;
        this.category = category;
    }

    public File getSeedfile() {
        return seedfile;
    }

    public SubCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getInformation() {
        return information;
    }

    /**
     * Website, irc or similar
     */
    public UploadTorrentRequest setInformation(String information) {
        this.information = Optional.ofNullable(information);
        return this;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public UploadTorrentRequest setDescription(String description) {
        this.description = Optional.ofNullable(description);
        return this;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public UploadTorrentRequest setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
        return this;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public UploadTorrentRequest setHidden(boolean hidden) {
        isHidden = hidden;
        return this;
    }

    public boolean isRemake() {
        return isRemake;
    }

    public UploadTorrentRequest setRemake(boolean remake) {
        isRemake = remake;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public UploadTorrentRequest setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }
}
