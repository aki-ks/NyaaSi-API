package de.kaysubs.tracker.nyaasi.util;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.model.SearchRequest;
import de.kaysubs.tracker.nyaasi.model.TorrentPreview;

import java.util.Iterator;

/**
 * Iterate over all search result.
 * This abstraction handles loading the next page for you.
 */
public class SearchIterator implements Iterator<TorrentPreview> {
    private final SearchRequest request;
    private final NyaaSiApi api;

    private TorrentPreview[] cache = null;
    private int index = 0;

    public SearchIterator(NyaaSiApi api, SearchRequest request) {
        this.api = api;
        this.request = request;
    }

    private void validateCache() {
        if(cache == null || (cache.length > 0 && index >= cache.length)) {
            cache = api.search(request);
            index = 0;

            request.setPage(request.getPage().orElse(1) + 1);
        }
    }

    @Override
    public boolean hasNext() {
        validateCache();
        return index < cache.length;
    }

    @Override
    public TorrentPreview next() {
        validateCache();
        return cache[index++];
    }
}
