package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    // 全ての支出データを取得するメソッド
    public List<KakeiboBean> getAllExpenses() throws SQLException {
        List<KakeiboBean> list = new ArrayList<>();
        String sql = "SELECT * FROM expense ORDER BY created_at DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // '支出' を type として、必要なデータを KakeiboBean にセット
                list.add(new KakeiboBean("支出", rs.getString("genre"), rs.getString("title"), rs.getInt("amount")));
            }
        }
        return list;
    }

    public static Map<Integer, Integer> getTotalExpenseByMonthGroupedByDay(String user_id, int year, int month) throws SQLException {
        Map<Integer, Integer> expenseByDay = new HashMap<>();

        String sql = "SELECT DAY(date) AS day, SUM(amount) AS total_expense " +
                "FROM expense " +
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
                    int totalExpense = rs.getInt("total_expense"); // 合計収入
                    expenseByDay.put(day, totalExpense); // 日付をキーにデータを格納
                }
            }
        }

        return expenseByDay; // 日付ごとの収入データを返す
    }

    public static int getTotalExpenseByMonth(String user_id, int year, int month) throws SQLException {
        int totalExpense = 0;
        // 年と月でフィルタリングし、収入額の合計を求めるSQLクエリ
        String sql = "SELECT SUM(amount) AS total_expense FROM expense WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダにパラメータをセット
            pstmt.setString(1, user_id);  // ユーザーIDをセット
            pstmt.setInt(2, year);      // 年をセット
            pstmt.setInt(3, month);     // 月をセット

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalExpense = rs.getInt("total_expense");  // 合計収入額を取得
                }
            }
        }
        return totalExpense;  // 合計収入額を返す
    }

    public static int getTotalExpenseByDate(String user_id, int year, int month, int day) throws SQLException {
        int totalExpense = 0;
        // 年、月、日でフィルタリングし、支出額の合計を求めるSQLクエリ
        String sql = "SELECT SUM(amount) AS total_expense FROM expense WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? AND DAY(date) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // プレースホルダにパラメータをセット
            pstmt.setString(1, user_id);  // ユーザーIDをセット
            pstmt.setInt(2, year);        // 年をセット
            pstmt.setInt(3, month);       // 月をセット
            pstmt.setInt(4, day);         // 日をセット

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalExpense = rs.getInt("total_expense");  // 合計支出額を取得
                }
            }
        }
        return totalExpense;  // 合計支出額を返す
    }


    // 特定の日付の支出データを取得するメソッド
    public static List<KakeiboBean> getExpenseByDate(String user_id, int year, int month, int day) throws SQLException {
        List<KakeiboBean> expenseList = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? AND DAY(date) = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_id);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setInt(4, day);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // KakeiboBean に取得したデータをセット
                    KakeiboBean bean = new KakeiboBean();
                    bean.setId(rs.getInt("id"));
                    bean.setTitle(rs.getString("title"));
                    bean.setAmount(rs.getInt("amount"));
                    bean.setDate(rs.getDate("date"));
                    bean.setGenre(rs.getString("genre"));  // 'category' を 'genre' に変更
                    expenseList.add(bean);
                }
            }
        }
        return expenseList;
    }

    public static Map<String, Integer> getExpenseByGenre(String user_id, int year, int month) throws SQLException {
        Map<String, Integer> expenseByGenre = new HashMap<>();

        String sql = "SELECT genre, SUM(amount) AS total_expense FROM expense WHERE user_id = ? AND YEAR(date) = ? AND MONTH(date) = ? GROUP BY genre";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user_id);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String genre = rs.getString("genre");
                    int totalExpense = rs.getInt("total_expense");
                    expenseByGenre.put(genre, totalExpense);
                }
            }
        }

        return expenseByGenre;
    }

    // 支出データをデータベースに保存するメソッド
    public void saveExpense(KakeiboBean expense) throws SQLException {
        String sql = "INSERT INTO expense (user_id, date, genre, title, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // それぞれのパラメータをセット
            pstmt.setString(1, expense.getUser_id());  // user_id をセット
            pstmt.setDate(2, expense.getDate());       // 日付をセット
            pstmt.setString(3, expense.getGenre());   // カテゴリをセット
            pstmt.setString(4, expense.getTitle());   // タイトルをセット
            pstmt.setInt(5, expense.getAmount());     // 金額をセット

            // SQL文を実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // エラーハンドリング
            throw new SQLException("Error while saving expense to database", e);
        }
    }

    public void updateExpense(KakeiboBean expense) throws SQLException {
        String sql = "UPDATE expense SET genre = ?, title = ?, amount = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expense.getGenre());
            pstmt.setString(2, expense.getTitle());
            pstmt.setInt(3, expense.getAmount());
            pstmt.setInt(4, expense.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while updating expense data", e);
        }
    }

    public void deleteExpense(int id) throws SQLException {
        String sql = "DELETE FROM expense WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while deleting expense data", e);
        }
    }
}
