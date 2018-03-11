package de.kaysubs.tracker.nyaasi.model;

public interface SubCategory extends Category {
    MainCategory getMainCategory();

    int getSubCategoryId();

    default int getMainCategoryId() {
        return getMainCategory().getMainCategoryId();
    }
}
