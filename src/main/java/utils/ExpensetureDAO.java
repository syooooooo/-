package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class ExpensetureDAO {
    private static final String URL = "jdbc:mysql://163.44.96.125:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true"; // データベースURL
    private static final String USER = "root"; // ユーザー名
    private static final String PASSWORD = "O-syougo0317"; // パスワード

    // データベース接続を取得するメソッド
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 全データを取得
    public List<ExpensetureBean> getAllExpenses(String user_id) {
        List<ExpensetureBean> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // user_idパラメータをセット
            statement.setString(1, user_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ExpensetureBean expense = new ExpensetureBean(
                            resultSet.getString("user_id"),
                            resultSet.getString("genre"),
                            resultSet.getString("title"),
                            resultSet.getInt("amount"),
                            resultSet.getInt("date")
                    );
                    expense.setId(resultSet.getInt("id"));
                    expenses.add(expense);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("expenses" + expenses);
        return expenses;
    }

    public static Map<Integer, Integer> getTotalExpensesGroupedByDay(String user_id) throws SQLException {
        Map<Integer, Integer> expensesByDay = new HashMap<>();

        String sql = "SELECT date AS day, SUM(amount) AS total_expense " +
                "FROM expenses " +
                "WHERE user_id = ? " +
                "GROUP BY date";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id); // ユーザーIDのみを設定

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int day = rs.getInt("day");  // 日付
                    int totalExpense = rs.getInt("total_expense"); // 合計収入
                    expensesByDay.put(day, totalExpense); // 日付をキーにデータを格納
                }
            }
        }
        System.out.println("expensesByDay" + expensesByDay);
        return expensesByDay; // 日付ごとの収入データを返す
    }


    public static int getTotalExpenses(String user_id) throws SQLException {
        int totalExpenses = 0;
        // ユーザーごとの合計支出を取得するSQLクエリ
        String sql = "SELECT SUM(amount) AS total_expense FROM expenses WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id);  // ユーザーIDをセット

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalExpenses = rs.getInt("total_expense");  // 合計支出額を取得
                }
            }
        }
        System.out.println(totalExpenses);
        return totalExpenses;  // 合計支出額を返す
    }


    public static List<ExpensetureBean> getExpensesByDate(String user_id, int day) throws SQLException {
        List<ExpensetureBean> expensesList = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? AND date = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, day);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    ExpensetureBean bean = new ExpensetureBean(
                            rs.getInt("id"),       // ID
                            rs.getString("genre"),   // ジャンル
                            rs.getString("title"),   // タイトル
                            rs.getInt("amount"),     // 金額
                            rs.getInt("date")
                    );

                    expensesList.add(bean);
                }
            }
        }
        return expensesList;
    }


    public static Map<String, Integer> getExpensesByGenre(String user_id) throws SQLException {
        Map<String, Integer> expensesByGenre = new HashMap<>();

        String sql = "SELECT genre, SUM(amount) AS total_expense FROM expenses WHERE user_id = ? GROUP BY genre";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String genre = rs.getString("genre");
                    int totalExpense = rs.getInt("total_expense");
                    expensesByGenre.put(genre, totalExpense);
                }
            }
        }
        System.out.println(expensesByGenre);
        return expensesByGenre;
    }

    // データを追加
    public void addExpense(ExpensetureBean expense) {
        String query = "INSERT INTO expenses (user_id, genre, title, amount, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expense.getUser_id());
            statement.setString(2, expense.getGenre());
            statement.setString(3, expense.getTitle());
            statement.setInt(4, expense.getAmount());
            statement.setInt(5, expense.getDay());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // タイトルに基づいてデータを削除
    public void deleteExpenseById(int id) {
        String query = "DELETE FROM expenses WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
