package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.model.TorrentInfo;

import java.util.Scanner;

public class TorrentInfoExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("is the torrent on sukebei [true/false]: ");
        boolean isSubekei = sc.nextBoolean();
        System.out.print("torrent id: ");
        int torrentId = sc.nextInt();

        NyaaSiApi api = isSubekei ? NyaaSiApi.getSukebei() : NyaaSiApi.getNyaa();
        TorrentInfo info = api.getTorrentInfo(torrentId);

        System.out.println("title: " + info.getTitle());
        System.out.println("category: " + info.getCategory().getMainCategory() + "." + info.getCategory());
        System.out.println("size: " + info.getSize());
        System.out.println("upload date: " + info.getDate());
        System.out.println("uploader: " + info.getUploader().orElse("Anonymous"));
        System.out.println("state: " + info.getTorrentState());
        System.out.println("seeders: " + info.getSeeders());
        System.out.println("leechers: " + info.getLeechers());
        System.out.println("completed: " + info.getCompleted());
        System.out.println("homepage/irc/etc.: " + info.getInformation());
        System.out.println("hash: " + info.getHash());
        System.out.println("download link: " + info.getDownloadLink());
        System.out.println("magnet link: " + info.getMagnetLink());
        System.out.println("description: " + info.getDescription());
        System.out.println("files:");
        printFile(info.getFile(), 0);

        for(TorrentInfo.Comment comment : info.getComments()) {
            System.out.println("[Comment]");
            System.out.println("user: " + comment.getUsername());
            System.out.println("trusted: " + comment.isTrusted());
            System.out.println("avatar: " + comment.getAvatar());
            System.out.println("date: " + comment.getDate());
            System.out.println(comment.getComment());
            System.out.println();
        }
    }

    private static void printFile(TorrentInfo.FileNode file, int offset) {
        for (int i = 0; i < offset; i++)
            System.out.print("  ");

        if(file instanceof TorrentInfo.File) {
            System.out.println(file.getName() + " (" + ((TorrentInfo.File) file).getSize() + ")");
        } else {
            System.out.println(file.getName());

            for(TorrentInfo.FileNode child : ((TorrentInfo.Folder) file).getChildren()) {
                printFile(child, offset + 1);
            }
        }
    }

}
