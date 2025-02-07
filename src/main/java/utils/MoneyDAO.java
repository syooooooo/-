package utils;

import java.sql.*;

public class MoneyDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8"; // 適切なURLに変更
    private final String DB_USER = "root"; // データベースユーザー名
    private final String DB_PASSWORD = "O-syougo0317"; // データベースパスワード

    // MoneyBean を返すメソッド
    public MoneyBean getMoneyBean(String userId) {
        MoneyBean moneyBean = new MoneyBean();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT goal_amount FROM money WHERE user_id = ?")) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                moneyBean.setGoalAmount(rs.getInt("goal_amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return moneyBean;
    }

    public boolean isGoalAmountExists(String userId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM money WHERE user_id = ?")) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 1以上なら登録済み
            }
        }
        return false; // レコードがない場合はfalseを返す
    }


    public void createGoalAmount(String userId, int goalAmount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO money (user_id, goal_amount) values (?, ?)")) {

            stmt.setString(1, userId);
            stmt.setInt(2, goalAmount);
            stmt.executeUpdate();
        }
    }

    // 目標金額を更新するメソッド
    public void updateGoalAmount(String userId, int goalAmount) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE money SET goal_amount = ? WHERE user_id = ?")) {

            stmt.setInt(1, goalAmount);
            stmt.setString(2, userId);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}