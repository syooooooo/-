package utils;

import java.sql.*;

public class UserDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8"; // 正しいMySQLのURL
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    // 登録メソッド
    static public void insert(UserBean user) {
        String sql = "INSERT INTO user (id, name, password, salt) VALUES (?, ?, ?, ?)";

        // ソルトの生成とハッシュ化
//        String salt = GenerateHash.getSalt();
//        String hashedPw = GenerateHash.getHashPw(user.getPassword(), salt);

        try (
                Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            // パラメータを設定
            pstmt.setString(1, user.getId());         // ID
            pstmt.setString(2, user.getName());       // 名前
            pstmt.setString(3, user.getPassword());   // ハッシュ化されたパスワード
            pstmt.setString(4, user.getSalt());       // ソルト

            // 実行
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // IDでユーザー情報を取得
    static public UserBean selectById(String id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        UserBean result = null;

        try (
                Connection con = DriverManager.getConnection(DB_URL, "root", "O-syougo0317");
                PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new UserBean(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("password"),  // 修正: "pw" -> "password"
                            rs.getString("salt")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static boolean isIdRegistered(String id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE id = ?"; // `users`はテーブル名
        try (
                Connection con = DriverManager.getConnection(DB_URL, "root", "O-syougo0317");
             PreparedStatement pstmt = con.prepareStatement(sql);
             ) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // IDが存在すればtrueを返す
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 呼び出し元でエラー処理
        }
        return false;
    }

}
