package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;

import java.util.Scanner;

public class CommentExamples {

    public static void main(String[] args) {
        NyaaSiAuthApi api = LoginExample.login();
        Scanner sc = new Scanner(System.in);
        int torrentId = sc.nextInt();

        writeComment(api, torrentId);
    }

    public static int writeComment(NyaaSiAuthApi api, int torrentId) {
        return api.writeComment(torrentId, "I'm testing a nyaa.si api");
    }

}
