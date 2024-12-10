package com.happytails.utils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

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


}
