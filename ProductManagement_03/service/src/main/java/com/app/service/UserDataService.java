package com.app.service;

import com.app.exception.MyException;

import java.util.Scanner;

public final class UserDataService {

    private UserDataService() {}

    private static Scanner scanner = new Scanner(System.in);

    public static int getInt(String message) {
        System.out.println(message);
        String text = scanner.nextLine();

        if (!text.matches("\\d+")) {
            throw new MyException("Int value is not correct");
        }

        return Integer.parseInt(text);
    }

    public static String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();

    }
}
