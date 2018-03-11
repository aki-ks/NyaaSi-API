package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;

import java.util.Scanner;

public class CommentExamples {

    public static void main(String[] args) {
        NyaaSiAuthApi api = LoginExample.login();
        Scanner sc = new Scanner(System.in);
        int torrentId = sc.nextInt();

        int commentId = api.writeComment(torrentId, "I'm testing a nyaa.si api");
        System.out.println("Example comment has id " + commentId);

        api.deleteComment(torrentId, commentId);
        System.out.println("Deleted example comment again");
    }

}
