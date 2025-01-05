package com.happytails.utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Utils {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    public static void openWebBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
                System.out.println("Web browser opened successfully.");
            } catch (IOException | URISyntaxException e) {
                System.err.println("Failed to open web browser: " + e.getMessage());
            }
        } else {
            System.err.println("Desktop is not supported. Cannot open web browser.");
        }
    }
    // Method to calculate the next birthday for a pet
    static LocalDate calculateNextBirthday(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        LocalDate nextBirthday = dateOfBirth.withYear(today.getYear());

        // If the next birthday is in the past, set it to next year
        if (nextBirthday.isBefore(today)) {
            nextBirthday = nextBirthday.withYear(today.getYear() + 1);
        }

        return nextBirthday;
    }

    // Method to get the correct suffix for the day (1st, 2nd, 3rd, 4th, etc.)
    public static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th"; // Special case for 11th, 12th, 13th
        }

        // General case for day suffixes
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }


}
