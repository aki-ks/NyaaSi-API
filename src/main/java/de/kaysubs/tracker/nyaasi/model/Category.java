package de.kaysubs.tracker.nyaasi.model;

public interface Category {
    int getMainCategoryId();
    int getSubCategoryId();
    boolean isSukebei();

    interface Nyaa extends Category {
        default boolean isSukebei() {
            return false;
        }

        enum Anime implements SubCategory, Category.Nyaa {
            AMV(1), ENGLISH(2), NON_ENGLISH(3), RAW(4);

            private final int subId;

            Anime(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.ANIME;
            }
        }

        enum Audio implements SubCategory, Category.Nyaa {
            LOSSLESS(1), LOSSY(2);

            private final int subId;

            Audio(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.AUDIO;
            }
        }

        enum Literature implements SubCategory, Category.Nyaa {
            ENGLISH(1), NON_ENGLISH(2), RAW(3);

            private final int subId;

            Literature(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.LITERATURE;
            }
        }

        enum LiveAction implements SubCategory, Category.Nyaa {
            ENGLISH(1), IDOL(2), NON_ENGLISH(3), RAW(4);

            private final int subId;

            LiveAction(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.LIVE_ACTION;
            }
        }

        enum Pictures implements SubCategory, Category.Nyaa {
            GRAPHICS(1), PHOTOS(2);

            private final int subId;

            Pictures(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.PICTURES;
            }
        }

        enum Software implements SubCategory, Category.Nyaa {
            APPS(1), GAMES(2);

            private final int subId;

            Software(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Nyaa getMainCategory() {
                return MainCategory.Nyaa.SOFTWARE;
            }
        }
    }

    interface Sukebei extends Category {
        default boolean isSukebei() {
            return true;
        }

        enum Art implements SubCategory, Category.Sukebei {
            ANIME(1), DOUJINSHI(2), GAMES(3), MANGA(4), PICTURES(5);

            private final int subId;

            Art(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Sukebei getMainCategory() {
                return MainCategory.Sukebei.ART;
            }
        }

        enum RealLife implements SubCategory, Category.Sukebei {
            PICTURES(1), VIDEOS(2);

            private final int subId;

            RealLife(int subId) {
                this.subId = subId;
            }

            @Override
            public int getSubCategoryId() {
                return subId;
            }

            @Override
            public MainCategory.Sukebei getMainCategory() {
                return MainCategory.Sukebei.READ_LIFE;
            }
        }
    }
}
