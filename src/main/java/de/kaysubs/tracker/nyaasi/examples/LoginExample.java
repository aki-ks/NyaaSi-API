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
        System.out.print("username: ");
        String username = sc.next();
        System.out.print("password: ");
        String password = sc.next();

        NyaaSiApi api = isSukebei ? NyaaSiApi.getSukebei() : NyaaSiApi.getNyaa();
        return api.login(username, password);
    }
}
