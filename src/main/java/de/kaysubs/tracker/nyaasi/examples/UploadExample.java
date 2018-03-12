package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;
import de.kaysubs.tracker.nyaasi.model.Category;
import de.kaysubs.tracker.nyaasi.model.SubCategory;
import de.kaysubs.tracker.nyaasi.model.UploadTorrentRequest;

import java.io.File;
import java.util.Scanner;

public class UploadExample {

    public static void main(String[] args) {
        NyaaSiAuthApi api = LoginExample.login();
        Scanner sc = new Scanner(System.in);
        System.out.print("path to .torrent file: ");
        File seedFile = new File(sc.next());

        int torrentId = uploadTorrent(api, seedFile);
        editTorrent(api, torrentId);
        deleteTorrent(api, torrentId);
    }

    private static int uploadTorrent(NyaaSiAuthApi api, File seedfile) {
        SubCategory category = api.isSukebei() ?
                Category.Sukebei.art.games :
                Category.Nyaa.software.games;

        int torrentId = api.uploadTorrent(new UploadTorrentRequest(seedfile, "API test torrent", category)
                .setDescription("This is an example torrent uploaded through 'k subs nyaa.si API.")
                .setInformation("http://example.com") // Link to your Homepage
                .setHidden(true) // this torrent should not be seen on the startpage
                .setAnonymous(true) // other users cannot see who uploaded this torrent
        );

        System.out.println("The example torrent was uploaded under the id " + torrentId);
        return torrentId;
    }

    private static void editTorrent(NyaaSiAuthApi api, int torrentId) {
        api.editTorrent(torrentId, torrent -> torrent
                .setName("EditedAPI test torrent")
                .setDescription("This torrent has been edited"));

        System.out.print("The example torrent was edited");
    }

    private static void deleteTorrent(NyaaSiAuthApi api, int torrentId) {
        api.deleteTorrent(torrentId);
        System.out.print("The example torrent was deleted again.");
    }

}
