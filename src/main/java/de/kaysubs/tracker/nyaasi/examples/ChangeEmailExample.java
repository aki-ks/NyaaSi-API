package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiApi;
import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;

import java.util.Scanner;

public class ChangeEmailExample {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final NyaaSiAuthApi authApi = LoginExample.login();

        System.out.print("password: ");
        String password = sc.next();
        System.out.print("new email: ");
        String newEmail = sc.next();

        authApi.changeEmail(password, newEmail);
    }

}
