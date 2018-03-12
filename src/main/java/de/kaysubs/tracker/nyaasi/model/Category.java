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

        class AnimeMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory amv = new SubCategory(this, "AMV", 1);
            public final SubCategory english = new SubCategory(this, "English", 2);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 3);
            public final SubCategory raw = new SubCategory(this, "Raw", 4);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { amv, english, nonEnglish, raw };
            }

            @Override
            public int getMainCategoryId() {
                return 1;
            }
        }

        class AudioMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory lossless = new SubCategory(this, "AMV", 1);
            public final SubCategory lossy = new SubCategory(this, "English", 2);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { lossless, lossy };
            }

            @Override
            public int getMainCategoryId() {
                return 2;
            }
        }

        class LiteratureMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory english = new SubCategory(this, "English", 1);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 2);
            public final SubCategory raw = new SubCategory(this, "Raw", 3);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { english, nonEnglish, raw };
            }

            @Override
            public int getMainCategoryId() {
                return 3;
            }
        }

        class LiveActionMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory english = new SubCategory(this, "English", 1);
            public final SubCategory idol = new SubCategory(this, "Idol", 2);
            public final SubCategory nonEnglish = new SubCategory(this, "NonEnglish", 3);
            public final SubCategory raw = new SubCategory(this, "Raw", 4);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { english, idol, nonEnglish, raw };
            }

            @Override
            public int getMainCategoryId() {
                return 4;
            }
        }

        class PicturesMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory graphics = new SubCategory(this, "Graphics", 1);
            public final SubCategory photos = new SubCategory(this, "Photos", 2);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { graphics, photos };
            }

            @Override
            public int getMainCategoryId() {
                return 5;
            }
        }

        class SoftwareMainCategory implements MainCategory, Category.Nyaa {
            public final SubCategory applications = new SubCategory(this, "Applications", 1);
            public final SubCategory games = new SubCategory(this, "Games", 2);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { applications, games };
            }

            @Override
            public int getMainCategoryId() {
                return 6;
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

        class ArtMainCategory implements MainCategory, Category.Sukebei {
            public final SubCategory anime = new SubCategory(this, "Anime", 1);
            public final SubCategory doujinshi = new SubCategory(this, "Doujinshi", 2);
            public final SubCategory games = new SubCategory(this, "Games", 3);
            public final SubCategory manga = new SubCategory(this, "Manga", 4);
            public final SubCategory pictures = new SubCategory(this, "Pictures", 5);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { anime, doujinshi, games, manga, pictures };
            }

            @Override
            public int getMainCategoryId() {
                return 1;
            }
        }

        class RealLifeMainCategory implements MainCategory, Category.Sukebei {
            public final SubCategory pictures = new SubCategory(this, "Pictures", 1);
            public final SubCategory videos = new SubCategory(this, "Videos", 2);

            @Override
            public SubCategory[] getSubCategories() {
                return new SubCategory[] { pictures, videos };
            }

            @Override
            public int getMainCategoryId() {
                return 2;
            }
        }

    }
}
