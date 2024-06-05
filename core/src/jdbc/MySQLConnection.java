package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dbGDXGameUser";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    public static void insertScore(int userId, int score) {
//        String query = "INSERT INTO high_scores (user_id, score) VALUES (?, ?)";
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, userId);
//            preparedStatement.setInt(2, score);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static HighScore getHighestScore() {
//        String query = "SELECT u.username, h.score " +
//                "FROM high_scores h " +
//                "JOIN users u ON h.user_id = u.id " +
//                "ORDER BY h.score DESC " +
//                "LIMIT 1";
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//            if (resultSet.next()) {
//                String username = resultSet.getString("username");
//                int score = resultSet.getInt("score");
//                return new HighScore(username, score);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static void main(String[] args) {
        getConnection();
    }
}



//package jdbc;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class MySQLConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/dbGDXGameUser";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "";
//
//    public static Connection getConnection(){
//        Connection connection = null;
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void main(String[] args) {
//        getConnection();
//    }
//}
