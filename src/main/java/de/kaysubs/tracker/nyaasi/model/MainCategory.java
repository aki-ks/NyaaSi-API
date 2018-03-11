package de.kaysubs.tracker.nyaasi.model;

import java.util.Arrays;

public interface MainCategory extends Category {
    SubCategory[] getSubCategories();

    int getMainCategoryId();
    default int getSubCategoryId() {
        return 0;
    }

    default SubCategory getSubcategoryFromId(int subId) {
        for(SubCategory subCategory : getSubCategories())
            if(subCategory.getSubCategoryId() == subId)
                return subCategory;

        throw new IllegalArgumentException("Category \"" + this.toString() + "\" has no subcategory with id " + subId);
    }

    enum Nyaa implements MainCategory, Category.Nyaa {
        ANIME(1, Category.Nyaa.Anime.values()),
        AUDIO(2, Category.Nyaa.Audio.values()),
        LITERATURE(3, Category.Nyaa.Literature.values()),
        LIVE_ACTION(4, Category.Nyaa.LiveAction.values()),
        PICTURES(5, Category.Nyaa.Pictures.values()),
        SOFTWARE(6, Category.Nyaa.Software.values());

        public static MainCategory.Nyaa fromId(int id) {
            return Arrays.stream(MainCategory.Nyaa.values())
                    .filter(c -> c.getMainCategoryId() == id).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No NyaaMainCategory with id " + id));
        }

        private final int id;
        private final SubCategory[] subCategories;

        Nyaa(int id, SubCategory[] subCategories) {
            this.id = id;
            this.subCategories = subCategories;
        }

        public int getMainCategoryId() {
            return id;
        }

        public SubCategory[] getSubCategories() {
            return subCategories;
        }
    }

    enum Sukebei implements MainCategory, Category.Sukebei {
        ART(1, Category.Sukebei.Art.values()),
        READ_LIFE(2, Category.Sukebei.RealLife.values());

        public static MainCategory.Sukebei fromId(int id) {
            return Arrays.stream(MainCategory.Sukebei.values())
                    .filter(c -> c.getMainCategoryId() == id).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No SukebeiMainCategory with id " + id));
        }

        private final int id;
        private final SubCategory[] subCategories;

        Sukebei(int id, SubCategory[] subCategories) {
            this.id = id;
            this.subCategories = subCategories;
        }

        public int getMainCategoryId() {
            return id;
        }

        public SubCategory[] getSubCategories() {
            return subCategories;
        }
    }

}
