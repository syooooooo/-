package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    public List<KakeiboBean> getAllIncome() throws SQLException {
        List<KakeiboBean> list = new ArrayList<>();
        String sql = "SELECT * FROM income ORDER BY created_at DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new KakeiboBean("収入", rs.getString("genre"), rs.getString("title"), rs.getInt("amount")));
            }
        }
        return list;
    }

    public static Map<Integer, Integer> getTotalIncomeByMonthGroupedByDay(String user_id, int year, int month) throws SQLException {
        Map<Integer, Integer> incomeByDay = new HashMap<>();

        String sql = "SELECT DAY(date) AS day, SUM(amount) AS total_income " +
                "FROM income " +
                "WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? " +
                "GROUP BY DAY(date)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id); // ユーザーID
            pstmt.setInt(2, year);      // 年
            pstmt.setInt(3, month);     // 月

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int day = rs.getInt("day");  // 日付
                    int totalIncome = rs.getInt("total_income"); // 合計収入
                    incomeByDay.put(day, totalIncome); // 日付をキーにデータを格納
                }
            }
        }

        return incomeByDay; // 日付ごとの収入データを返す
    }


    public static int getTotalIncomeByDate(String user_id, int year, int month, int day) throws SQLException {
        int totalIncome = 0;
        // 年、月、日でフィルタリングし、支出額の合計を求めるSQLクエリ
        String sql = "SELECT SUM(amount) AS total_income FROM income WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? AND DAY(date) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダにパラメータをセット
            pstmt.setString(1, user_id);  // ユーザーIDをセット
            pstmt.setInt(2, year);        // 年をセット
            pstmt.setInt(3, month);       // 月をセット
            pstmt.setInt(4, day);         // 日をセット

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalIncome = rs.getInt("total_income");  // 合計支出額を取得
                }
            }
        }
        return totalIncome;  // 合計支出額を返す
    }

    public static int getTotalIncomeByMonth(String user_id, int year, int month) throws SQLException {
        int totalIncome = 0;
        // 年と月でフィルタリングし、収入額の合計を求めるSQLクエリ
        String sql = "SELECT SUM(amount) AS total_income FROM income WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダにパラメータをセット
            pstmt.setString(1, user_id);  // ユーザーIDをセット
            pstmt.setInt(2, year);      // 年をセット
            pstmt.setInt(3, month);     // 月をセット

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalIncome = rs.getInt("total_income");  // 合計収入額を取得
                }
            }
        }
        return totalIncome;  // 合計収入額を返す
    }


    public static List<KakeiboBean> getIncomeByDate(String user_id, int year, int month, int day) throws SQLException {
        List<KakeiboBean> incomeList = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? AND DAY(date) = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, day);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KakeiboBean bean = new KakeiboBean();
                    bean.setId(rs.getInt("id"));
                    bean.setTitle(rs.getString("title"));
                    bean.setAmount(rs.getInt("amount"));
                    bean.setDate(rs.getDate("date"));
                    bean.setGenre(rs.getString("genre"));
                    incomeList.add(bean);
                }
            }
        }
        return incomeList;
    }

    public static Map<String, Integer> getIncomeByGenre(String user_id, int year, int month) throws SQLException {
        Map<String, Integer> incomeByGenre = new HashMap<>();

        String sql = "SELECT genre, SUM(amount) AS total_income FROM income WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? GROUP BY genre";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String genre = rs.getString("genre");
                    int totalIncome = rs.getInt("total_income");
                    incomeByGenre.put(genre, totalIncome);
                }
            }
        }

        return incomeByGenre;
    }


    public void saveIncome(KakeiboBean income) throws SQLException {
        String sql = "INSERT INTO income (user_id, date, genre, title, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // それぞれのパラメータをセット
            pstmt.setString(1, income.getUser_id());  // user_id をセット
            pstmt.setDate(2, income.getDate());       // 日付をセット
            pstmt.setString(3, income.getGenre());   // カテゴリをセット
            pstmt.setString(4, income.getTitle());   // タイトルをセット
            pstmt.setInt(5, income.getAmount());     // 金額をセット

            // SQL文を実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // エラーハンドリング
            throw new SQLException("Error while saving expense to database", e);
        }
    }

    public void updateIncome(KakeiboBean income) throws SQLException {
        String sql = "UPDATE income SET genre = ?, title = ?, amount = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, income.getGenre());
            pstmt.setString(2, income.getTitle());
            pstmt.setInt(3, income.getAmount());
            pstmt.setInt(4, income.getId());
//            pstmt.executeUpdate();

            System.out.println("Executing SQL: " + sql);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            throw new SQLException("Error while updating income data", e);
        }
    }

    public void deleteIncome(int id) throws SQLException {
        String sql = "DELETE FROM income WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while deleting income data", e);
        }
    }
}