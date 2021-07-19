package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;

import java.util.Scanner;

public class LoginExample {
    public static void main(String[] args) {
        login();
    }

    public static NyaaSiAuthApi login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("login at sukebei [true/false]: ");
        boolean isSukebei = sc.nextBoolean();
        System.out.print("session cookie (log in at https://" + (isSukebei ? "sukebei." : "") + "nyaa.si/login and paste your session token from cookies here):");
        String sessionCookie = sc.next();

        NyaaSiApi api = isSukebei ? NyaaSiApi.getSukebei() : NyaaSiApi.getNyaa();
        return api.login(sessionCookie);
    }
}
