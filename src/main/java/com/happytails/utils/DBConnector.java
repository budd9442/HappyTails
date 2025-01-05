package com.happytails.utils;

import com.happytails.models.Pet;

import java.io.Console;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static com.happytails.utils.Utils.calculateNextBirthday;
import static com.happytails.utils.Utils.getDaySuffix;

public class DBConnector {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/happytails?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    public static String currentUserID = null;
    public static Pet selectedPet = null;
    public static boolean debugMode = false;


    public static <T> List<T> query(String query, String[] args, Function<ResultSet, T> mapper) {
        List<T> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapper.apply(resultSet));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return results;
    }

    public static int executeUpdate(String query, String[] args) {
        if(debugMode) System.out.println("Executing update quary : "+ query + Arrays.toString(args));
        int rowsAffected = 0;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }

            rowsAffected = preparedStatement.executeUpdate(); // Executes update and returns affected row count

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rowsAffected;
    }


    public static String login(String username, String password) {
        String query = "SELECT user_id FROM user WHERE email=? AND password=?";
        List<String> results = DBConnector.query(query, new String[]{username, password}, resultSet -> {
            try {
                return resultSet.getString("user_id");
            } catch (SQLException e) {
                return null;
            }
        });
        return results.isEmpty() ? null : results.getFirst();
    }

    public static int register(String name, String email, String password){
        String query = "INSERT into user VALUES( uuid() , ? , ? , ? ,NULL, NULL)";
        return executeUpdate(query, new String[]{name, email,password});
    }

    public static String getUpcomingBirthday() {
        String query = "SELECT PetName, DateOfBirth FROM pet";

        // Query the database to get all pets' DateOfBirths
        List<String> results = query(query, new String[]{}, resultSet -> {
            try {
                String petName = resultSet.getString("PetName");
                LocalDate dateOfBirth = resultSet.getDate("DateOfBirth").toLocalDate();
                // Calculate next birthday directly here
                LocalDate nextBirthday = calculateNextBirthday(dateOfBirth);

                // Get the day with the appropriate suffix
                int day = nextBirthday.getDayOfMonth();
                String daySuffix = getDaySuffix(day);
                String month = nextBirthday.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                // Return a formatted string
                return String.format("Next Birthday: %s - %d%s %s", petName, day, daySuffix, month);
            } catch (SQLException e) {
                return null;
            }
        });

        // Find the soonest upcoming birthday
        for (String result : results) {
            if (result != null) {
                return result; // Return the formatted result for the first found pet
            }
        }

        return "";
    }


    public static String getNextAppointmentDate() {
        String query = "SELECT date FROM appointments WHERE date >= CURDATE() ORDER BY date LIMIT 1";

        // Query the database to get the soonest appointment
        List<String> results = query(query, new String[]{}, resultSet -> {
            try {
                LocalDate date = resultSet.getDate("date").toLocalDate();

                // Format date for output
                return "Next Appointment : "+ date.getDayOfMonth() + getDaySuffix(date.getDayOfMonth()) + " " + date.getMonth().name().substring(0, 1).toUpperCase() + date.getMonth().name().substring(1).toLowerCase();
            } catch (SQLException e) {
                return null;
            }
        });

        // Return the result for the soonest appointment
        if (!results.isEmpty()) {
            return results.get(0); // Return the formatted date for the next appointment
        }

        return "No upcoming appointments.";
    }


    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
