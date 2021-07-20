package de.kaysubs.tracker.nyaasi.model;

public class EditTorrentRequest {
    private final String csrfToken;
    private String name;
    private SubCategory category;
    private String information;
    private String description;
    private boolean isAnonymous;
    private boolean isHidden;
    private boolean isRemake;
    private boolean isCompleted;

    /**
     * @deprecated CSRF tokens are no longer used on nyaa.si.
     * Use {@link #EditTorrentRequest(String, SubCategory, String, String, boolean, boolean, boolean, boolean)} instead.
     */
    @Deprecated
    public EditTorrentRequest(String csrfToken, String name, SubCategory category, String information, String description,
                              boolean isAnonymous, boolean isHidden, boolean isRemake, boolean isCompleted) {
        this.csrfToken = csrfToken;
        this.name = name;
        this.category = category;
        this.information = information;
        this.description = description;
        this.isAnonymous = isAnonymous;
        this.isHidden = isHidden;
        this.isRemake = isRemake;
        this.isCompleted = isCompleted;
    }

    public EditTorrentRequest(String name, SubCategory category, String information, String description,
                              boolean isAnonymous, boolean isHidden, boolean isRemake, boolean isCompleted) {
        this(null, name, category, information, description, isAnonymous, isHidden, isRemake, isCompleted);

    }

    /**
     * @deprecated CSRF tokens are no longer used on nyaa.si.
     */
    @Deprecated
    public String getCsrfToken() {
        return csrfToken;
    }

    public SubCategory getCategory() {
        return category;
    }

    public EditTorrentRequest setCategory(SubCategory category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditTorrentRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getInformation() {
        return information;
    }

    /**
     * Website, irc or similar
     */
    public EditTorrentRequest setInformation(String information) {
        this.information = information;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditTorrentRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public EditTorrentRequest setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
        return this;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public EditTorrentRequest setHidden(boolean hidden) {
        isHidden = hidden;
        return this;
    }

    public boolean isRemake() {
        return isRemake;
    }

    public EditTorrentRequest setRemake(boolean remake) {
        isRemake = remake;
        return this;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public EditTorrentRequest setCompleted(boolean completed) {
        isCompleted = completed;
        return this;
    }
}
