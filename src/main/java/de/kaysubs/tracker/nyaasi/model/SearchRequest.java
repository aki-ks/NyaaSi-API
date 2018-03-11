package de.kaysubs.tracker.nyaasi.model;

import java.util.Optional;
import java.util.OptionalInt;

public class SearchRequest {
    private Optional<String> term = Optional.empty();
    private Optional<Category> category = Optional.empty();
    private Optional<Filter> filter = Optional.empty();
    private Optional<String> user = Optional.empty();
    private OptionalInt page = OptionalInt.empty();
    private Optional<Ordering> ordering = Optional.empty();
    private Optional<Sort> sortedBy = Optional.empty();

    public enum Filter {
        NONE(0), NO_REMAKES(1), TRUSTED_ONLY(2);
        private final int id;

        Filter(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum Ordering {
        ASCENDING("asc"), DESCENDING("desc");
        private final String id;

        Ordering(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public enum Sort {
        COMMENTS("comments"),
        SIZE("size"),
        DATE("id"),
        SEEDERS("seeders"),
        LEECHERS("seeders"),
        DOWNLOADS("downloads");
        private final String id;

        Sort(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public Optional<String> getTerm() {
        return term;
    }

    public SearchRequest setTerm(String term) {
        this.term = Optional.ofNullable(term);
        return this;
    }

    public Optional<Category> getCategory() {
        return category;
    }

    public SearchRequest setCategory(Category category) {
        this.category = Optional.ofNullable(category);
        return this;
    }

    public Optional<Filter> getFilter() {
        return filter;
    }

    public SearchRequest setFilter(Filter filter) {
        this.filter = Optional.ofNullable(filter);
        return this;
    }

    public Optional<String> getUser() {
        return user;
    }

    public SearchRequest setUser(String user) {
        this.user = Optional.ofNullable(user);
        return this;
    }

    public OptionalInt getPage() {
        return page;
    }

    /**
     * Page of results to return,
     * where page 1 is the first one.
     */
    public SearchRequest setPage(Integer page) {
        this.page = page == null ? OptionalInt.empty() : OptionalInt.of(page);
        return this;
    }

    public Optional<Ordering> getOrdering() {
        return ordering;
    }

    public SearchRequest setOrdering(Ordering ordering) {
        this.ordering = Optional.ofNullable(ordering);
        return this;
    }

    public Optional<Sort> getSortedBy() {
        return sortedBy;
    }

    public SearchRequest setSortedBy(Sort sortBy) {
        this.sortedBy = Optional.ofNullable(sortBy);
        return this;
    }
}
