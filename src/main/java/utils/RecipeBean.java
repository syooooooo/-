package utils;

import java.sql.Timestamp;

public class RecipeBean {
    private int id;
    private String user_id;  // ユーザーID
    private String name;  // レシピ名
    private String description;  // レシピの説明
    private String ingredients;  // 材料
    private String instructions;  // 作り方
    private Timestamp createdAt;  // 作成日時
    private Timestamp updatedAt;  // 更新日時
    private boolean isFavorite;
    private String favoriteClass;

    // コンストラクタ
    public RecipeBean(String user_id, String name, String description, String ingredients, String instructions) {
        this.user_id = user_id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.isFavorite = false;
    }

    public RecipeBean(int id, String userId, String name, String description, String ingredients, String instructions) {
        this.id = id;
        this.user_id = userId;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public RecipeBean(){}

    // ゲッターとセッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getFavoriteClass() { return favoriteClass; } public void setFavoriteClass(String favoriteClass) { this.favoriteClass = favoriteClass; }

    // データベース操作時にIDを設定する
    public void setDatabaseFields(int id, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "RecipeBean{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
