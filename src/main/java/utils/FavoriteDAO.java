package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWD = "O-syougo0317";

    // 特定のユーザーのお気に入りレシピを取得
    public static List<FavoriteBean> getFavoritesByUserId(String userId) {
        String sql = """
            SELECT
                recipe.id AS recipe_id,
                recipe.name AS recipe_name,
                recipe.description,
                recipe.ingredients,
                recipe.instructions
            FROM
                favorite
            INNER JOIN
                recipe
            ON
                favorite.recipe_id = recipe.id
            WHERE
                favorite.user_id = ?
        """;

        List<FavoriteBean> favorites = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FavoriteBean recipe = new FavoriteBean();
                    recipe.setId(rs.getInt("recipe_id"));
                    recipe.setName(rs.getString("recipe_name"));
                    recipe.setDescription(rs.getString("description"));
                    recipe.setIngredients(rs.getString("ingredients"));
                    recipe.setInstructions(rs.getString("instructions"));
                    favorites.add(recipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching favorite recipes", e);
        }

        return favorites;
    }

    public static boolean isFavorite(String userId, int recipeId) {
        String sql = "SELECT COUNT(*) FROM favorite WHERE user_id = ? AND recipe_id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 1以上なら登録済み
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addFavorite(String userId, int recipeId) {
        String sql = "INSERT INTO favorite (user_id, recipe_id, created_at) VALUES (?, ?, NOW())";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFavorite(String userId, int recipeId) {
        String sql = "DELETE FROM favorite WHERE user_id = ? AND recipe_id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASSWD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, recipeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
