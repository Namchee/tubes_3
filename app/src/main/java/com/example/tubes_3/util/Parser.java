package com.example.tubes_3.util;

import java.util.Date;

public class Parser {
    public static Date parseDate(String timestamp) {
        Date date = null;

        try {
            long time = Long.parseLong(timestamp);
            date = new java.util.Date(time*1000L);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String parseStatus(int statusCode) {
        switch (statusCode) {
            case 1:
                return "On going";
            case 2:
                return "Completed";
            default:
                return "SELAMAT MEMBACA API";
        }
    }
}
