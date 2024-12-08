package com.happytails.utils;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class DBConnector {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/happytails?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    public static String currentUserID = null;
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
        String query = "INSERT into user VALUES( uuid() , ? , ? , ? )";
        return executeUpdate(query, new String[]{name, email,password});
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
