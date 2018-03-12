package de.kaysubs.tracker.nyaasi.model;

import java.util.Arrays;

public interface Category {
    int getMainCategoryId();
    int getSubCategoryId();
    boolean isSukebei();

    interface Nyaa extends Category {
        default boolean isSukebei() {
            return false;
        }

        AnimeMainCategory anime = new AnimeMainCategory();
        AudioMainCategory audio = new AudioMainCategory();
        LiteratureMainCategory literature = new LiteratureMainCategory();
        LiveActionMainCategory liveAction = new LiveActionMainCategory();
        PicturesMainCategory pictures = new PicturesMainCategory();
        SoftwareMainCategory software = new SoftwareMainCategory();

        MainCategory[] mainCategories = new MainCategory[] {
                anime, audio, literature, liveAction, pictures, software
        };

        static MainCategory fromId(int mainCategoryId) {
            return Arrays.stream(Nyaa.mainCategories)
                    .filter(c -> c.getMainCategoryId() == mainCategoryId).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No nyaa MainCategory with id " + mainCategoryId));
        }

        class AnimeMainCategory extends MainCategory implements Category.Nyaa {
            public final SubCategory amv = new SubCategory(this, "AMV", 1);
            public final SubCategory english = new SubCategory(this, "English", 2);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 3);
            public final SubCategory raw = new SubCategory(this, "Raw", 4);

            private AnimeMainCategory() {
                super(1);
            }
        }

        class AudioMainCategory extends MainCategory implements Category.Nyaa {
            public final SubCategory lossless = new SubCategory(this, "AMV", 1);
            public final SubCategory lossy = new SubCategory(this, "English", 2);

            private AudioMainCategory() {
                super(2);
            }
        }

        class LiteratureMainCategory extends MainCategory implements Nyaa {
            public final SubCategory english = new SubCategory(this, "English", 1);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 2);
            public final SubCategory raw = new SubCategory(this, "Raw", 3);

            private LiteratureMainCategory() {
                super(3);
            }
        }

        class LiveActionMainCategory extends MainCategory implements Nyaa {
            public final SubCategory english = new SubCategory(this, "English", 1);
            public final SubCategory idol = new SubCategory(this, "Idol", 2);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 3);
            public final SubCategory raw = new SubCategory(this, "Raw", 4);

            private LiveActionMainCategory() {
                super(4);
            }
        }

        class PicturesMainCategory extends MainCategory implements Nyaa {
            public final SubCategory graphics = new SubCategory(this, "Graphics", 1);
            public final SubCategory photos = new SubCategory(this, "Photos", 2);

            private PicturesMainCategory() {
                super(5);
            }
        }

        class SoftwareMainCategory extends MainCategory implements Nyaa {
            public final SubCategory applications = new SubCategory(this, "Applications", 1);
            public final SubCategory games = new SubCategory(this, "Games", 2);

            private SoftwareMainCategory() {
                super(6);
            }
        }
    }

    interface Sukebei extends Category {
        default boolean isSukebei() {
            return true;
        }

        ArtMainCategory art = new ArtMainCategory();
        RealLifeMainCategory realLife = new RealLifeMainCategory();

        MainCategory[] mainCategories = new MainCategory[] {
                art, realLife
        };

        static MainCategory fromId(int mainCategoryId) {
            return Arrays.stream(Sukebei.mainCategories)
                    .filter(c -> c.getMainCategoryId() == mainCategoryId).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No sukebei MainCategory with id " + mainCategoryId));
        }

        class ArtMainCategory extends MainCategory implements Sukebei {
            public final SubCategory anime = new SubCategory(this, "Anime", 1);
            public final SubCategory doujinshi = new SubCategory(this, "Doujinshi", 2);
            public final SubCategory games = new SubCategory(this, "Games", 3);
            public final SubCategory manga = new SubCategory(this, "Manga", 4);
            public final SubCategory pictures = new SubCategory(this, "Pictures", 5);

            private ArtMainCategory() {
                super(1);
            }
        }

        class RealLifeMainCategory extends MainCategory implements Sukebei {
            public final SubCategory pictures = new SubCategory(this, "Pictures", 1);
            public final SubCategory videos = new SubCategory(this, "Videos", 2);

            private RealLifeMainCategory() {
                super(2);
            }
        }

    }
}
