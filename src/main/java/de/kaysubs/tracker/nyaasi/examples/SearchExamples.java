package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.model.*;
import de.kaysubs.tracker.nyaasi.util.SearchIterator;

import java.util.Iterator;

public class SearchExamples {

    public static void printLatestTorrents() {
        TorrentPreview[] nyaaTorrents = NyaaSiApi.getNyaa().search(new SearchRequest());
        TorrentPreview[] sukebeiTorrents = NyaaSiApi.getSukebei().search(new SearchRequest());

        System.out.println("Here are the latest uploads on nyaa.si:");
        for(TorrentPreview torrent : nyaaTorrents)
            System.out.print(torrent.getTitle());

        System.out.println("Here are the latest uploads on sukebei.nyaa.si:");
        for(TorrentPreview torrent : sukebeiTorrents)
            System.out.print(torrent.getTitle());
    }

    public static void getDetailsOfAResult() {
        TorrentPreview latestUpload = NyaaSiApi.getNyaa().search(new SearchRequest())[0];

        TorrentInfo info = NyaaSiApi.getNyaa().getTorrentInfo(latestUpload.getId());

        System.out.println("The latest torrent was uploaded by the user " + info.getUploader());
    }

    public static void searchForTorrent() {
        NyaaSiApi.getNyaa().search(new SearchRequest().setTerm("Overlord"));
    }

    public static void iterateOverSearchResult() {
        Iterator<TorrentPreview> iter = new SearchIterator(NyaaSiApi.getNyaa(),
                new SearchRequest().setTerm("Overlord"));

        iter.forEachRemaining(torrent ->
                System.out.println(torrent.getTitle()));
    }

    public static void filterByCategory() {
        NyaaSiApi.getNyaa().search(new SearchRequest()
                .setCategory(MainCategory.Nyaa.ANIME));

        NyaaSiApi.getNyaa().search(new SearchRequest()
                .setCategory(Category.Nyaa.Anime.ENGLISH));

        // Use Category.Sukebei / MainCategory.Sukebei to search on sukebei.nyaa.si
        NyaaSiApi.getSukebei().search(new SearchRequest()
                .setCategory(Category.Sukebei.Art.ANIME));
    }

    public static void usingFilters() {
        // Do not shows torrents that are marked as remakes
        NyaaSiApi.getNyaa().search(new SearchRequest()
                .setFilter(SearchRequest.Filter.NO_REMAKES));

        // Show only torrents uploaded by trusted users
        NyaaSiApi.getNyaa().search(new SearchRequest()
                .setFilter(SearchRequest.Filter.TRUSTED_ONLY));
    }

    public static void listTorrentsOfUser(String username) {
        NyaaSiApi.getNyaa().search(new SearchRequest().setUser(username));
        NyaaSiApi.getSukebei().search(new SearchRequest().setUser(username));
    }

    public static void orderingAndSorting() {
        TorrentPreview oldestTorrent = NyaaSiApi.getNyaa().search(new SearchRequest()
                // Sort by age
                .setSortedBy(SearchRequest.Sort.DATE)
                // Show in ascending order (oldest first, latest last)
                .setOrdering(SearchRequest.Ordering.ASCENDING))[0];

        TorrentPreview largestTorrent = NyaaSiApi.getNyaa().search(new SearchRequest()
                .setSortedBy(SearchRequest.Sort.SIZE)
                .setOrdering(SearchRequest.Ordering.DESCENDING))[0];

        TorrentPreview smallestTorrent = NyaaSiApi.getNyaa().search(new SearchRequest()
                .setSortedBy(SearchRequest.Sort.SIZE)
                .setOrdering(SearchRequest.Ordering.ASCENDING))[0];
    }
}
