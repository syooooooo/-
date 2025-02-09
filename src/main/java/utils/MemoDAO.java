package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemoDAO {

    private static final String DB_URL = "jdbc:mysql://163.44.96.125:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true"; // 正しいMySQLのURL
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    // メモの作成
    public static void createMemo(String userId, String title, String content) throws SQLException {
        String query = "INSERT INTO memo (user_id, title, text) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }
    }

    // 特定のユーザーのメモを全て取得
    public static List<MemoBean> getMemosByUserId(String userId) throws SQLException {
        List<MemoBean> memoList = new ArrayList<>();
        String query = "SELECT * FROM memo WHERE user_id = ? ORDER BY update_day DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("text");
                Timestamp createdAt = rs.getTimestamp("create_day");
                Timestamp updatedAt = rs.getTimestamp("update_day");
                memoList.add(new MemoBean(id, userId, title, content, createdAt, updatedAt));
            }
        }
        return memoList;
    }

    // 特定のメモIDでメモを取得
    public static MemoBean getMemoById(int memoId) throws SQLException {
        String query = "SELECT * FROM memo WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, memoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String userId = rs.getString("user_id");
                String title = rs.getString("title");
                String content = rs.getString("text");
                Timestamp createdAt = rs.getTimestamp("create_day");
                Timestamp updatedAt = rs.getTimestamp("update_day");
                return new MemoBean(id, userId, title, content, createdAt, updatedAt);
            }
        }
        return null;  // メモが存在しない場合
    }

    // メモの更新
    public static void updateMemo(int memoId, String title, String content) throws SQLException {
        String query = "UPDATE memo SET title = ?, text = ?, update_day = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, memoId);
            stmt.executeUpdate();
        }
    }

    // メモの削除
    public static void deleteMemo(int memoId) throws SQLException {
        String query = "DELETE FROM memo WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, memoId);
            stmt.executeUpdate();
        }
    }
}