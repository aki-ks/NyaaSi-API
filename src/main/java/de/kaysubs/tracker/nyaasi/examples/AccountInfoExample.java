package de.kaysubs.tracker.nyaasi.examples;

import de.kaysubs.tracker.nyaasi.NyaaSiAuthApi;
import de.kaysubs.tracker.nyaasi.model.AccountInfo;

public class AccountInfoExample {

    public static void main(String[] args) {
        NyaaSiAuthApi api = LoginExample.login();

        AccountInfo info = api.getAccountInfo();

        System.out.println("name: " + info.getName());
        System.out.println("email: " + info.getEmail());
        System.out.println("avatar: " + info.getAvatarUrl());
        System.out.println("user id: " + info.getUserId());
        System.out.println("user class: " + info.getUserClass());
        System.out.println("registration date: " + info.getRegistrationDate());
    }

}
