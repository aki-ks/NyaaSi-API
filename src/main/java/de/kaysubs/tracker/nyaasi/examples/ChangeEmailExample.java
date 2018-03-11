package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;

import java.util.Scanner;

public class ChangeEmailExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("login at sukebei [true/false]: ");
        boolean isSukebei = sc.nextBoolean();
        System.out.print("username: ");
        String username = sc.next();
        System.out.print("password: ");
        String password = sc.next();
        System.out.print("new email: ");
        String newEmail = sc.next();

        NyaaSiApi api = isSukebei ? NyaaSiApi.getSukebei() : NyaaSiApi.getNyaa();
        NyaaSiAuthApi authApi = api.login(username, password);

        authApi.changeEmail(password, newEmail);
    }

}
