package de.kaysubs.tracker.nyaasi.model;

public class SubCategory implements Category {
    private final MainCategory mainCategory;
    private final String name;
    private final int id;

    public SubCategory(MainCategory mainCategory, String name, int id) {
        this.mainCategory = mainCategory;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public int getSubCategoryId() {
        return id;
    }

    public int getMainCategoryId() {
        return mainCategory.getMainCategoryId();
    }

    @Override
    public boolean isSukebei() {
        return mainCategory.isSukebei();
    }
}
