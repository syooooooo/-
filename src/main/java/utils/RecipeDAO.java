package utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class RecipeDAO {
    private static final String DB_URL = "jdbc:mysql://163.44.96.125:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    // レシピをテーブルに登録するメソッド
    public static void insertRecipe(RecipeBean recipe) throws FileNotFoundException {
        String sql = "INSERT INTO recipe (user_id, name, description, ingredients, instructions) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, recipe.getUser_id());
            pstmt.setString(2, recipe.getName());
            pstmt.setString(3, recipe.getDescription());
            pstmt.setString(4, recipe.getIngredients());
            pstmt.setString(5, recipe.getInstructions());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting recipe", e);
        }
    }

    public static void editRecipes(int id, String name, String description, String ingredients, String instructions) throws FileNotFoundException {
        String sql = "UPDATE recipe SET name = ?, description = ?, ingredients = ?, instructions = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, ingredients);
            pstmt.setString(4, instructions);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();

            int rowsUpdated = pstmt.executeUpdate();  // 実際に更新された行数を取得
            if (rowsUpdated == 0) {
                throw new SQLException("No rows updated for recipe id " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while inserting recipe", e);
        }
    }

    public static void deleteRecipes(int recipeId) throws SQLException {
        String query = "DELETE FROM recipe WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // トランザクション開始
            conn.setAutoCommit(false);

            // パラメータ設定
            stmt.setInt(1, recipeId);

            // SQL実行
            int rowsAffected = stmt.executeUpdate();

            // 結果確認
            if (rowsAffected > 0) {
                System.out.println("レシピが削除されました。");
            } else {
                System.out.println("指定されたレシピは存在しません。");
            }

            // コミット
            conn.commit();
        } catch (SQLException e) {
            // エラーハンドリング
            e.printStackTrace();
            throw e;
        } finally {
            // 自動コミットに戻す
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD)) {
                conn.setAutoCommit(true);
            }
        }
    }

    public static List<RecipeBean> getAllRecipes(String userId) throws SQLException {
        List<RecipeBean> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipe WHERE user_id = ? ORDER BY created_at DESC";

        // 変数 userId を final または実質的に final にします
        final String finalUserId = userId;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, finalUserId);  // finalUserId を使用

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RecipeBean recipe = new RecipeBean(
                            rs.getString("user_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("ingredients"),
                            rs.getString("instructions")
                    );
                    // id, createdAt, updatedAt を設定
                    recipe.setDatabaseFields(
                            rs.getInt("id"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                    recipes.add(recipe);
                }
            }
        }

        // レシピ内容をログに出力して確認
        for (RecipeBean recipe : recipes) {
            System.out.println(recipe);
        }

        return recipes;
    }

    public static List<RecipeBean> searchRecipes(String user_id, String keyword) {
        List<RecipeBean> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe WHERE user_id = ? AND (name LIKE ? OR ingredients LIKE ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // 検索キーワードにワイルドカードを追加
            stmt.setString(1, user_id);
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RecipeBean recipe = new RecipeBean();
                    recipe.setId(rs.getInt("id"));
                    recipe.setName(rs.getString("name"));
                    recipe.setIngredients(rs.getString("ingredients"));
                    recipe.setDescription(rs.getString("description"));
                    recipes.add(recipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public static RecipeBean getRecipeById(int recipeId) throws SQLException {
        String query = "SELECT * FROM recipe WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String userId = rs.getString("user_id");

                String name = rs.getString("name");
                String description = rs.getString("description");
                String ingredients = rs.getString("ingredients");
                String instructions = rs.getString("instructions");

                return new RecipeBean(id, userId, name, description, ingredients, instructions);
            }

            return null;  // メモが存在しない場合
        }
    }
}
