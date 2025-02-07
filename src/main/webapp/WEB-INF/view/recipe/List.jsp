<%@ page import="utils.RecipeBean" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.FavoriteDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>レシピ一覧</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="list">
<div class="body">
    <div class="head">
        <button class="back" type="button"  onclick="history.back()">
            <i class="fa-solid fa-circle-left"></i>
        </button>
        <div class="link">
            <a href="ListRecipeServlet">すべてのレシピ</a>
            <a href="FavoriteServlet">お気に入り</a>
        </div>
    </div>
    <div class="h2">
        <h2>すべてのレシピ</h2>
    </div>
    <div class="results">
        <%
            // レシピリストの取得とチェック
            List<RecipeBean> recipes = (List<RecipeBean>) request.getAttribute("recipes");
            String userId = (String) session.getAttribute("user_id");
            if (recipes == null || recipes.isEmpty()) {
        %>
        <p>現在、表示できるレシピはありません。</p>
        <% } else {
            for (RecipeBean recipe : recipes) {
                String recipeClass = FavoriteDAO.isFavorite(userId, recipe.getId()) ? "registered" : "unregistered";
        %>
        <a href="RecipeDetailServlet?recipeId=<%= recipe.getId() %>" class="recipe-card <%= recipeClass %>">
<%--            <img src="#" alt="レシピ画像">--%>
            <div class="recipe-details">
                <h3><%= recipe.getName() %></h3>
                <p>材料: <%= recipe.getIngredients() %></p>
            </div>
            <div class="recipe-actions">
                <form action="FavoriteServlet" method="post">
                    <input type="hidden" name="userId" value="<%= session.getAttribute("user_id") %>">
                    <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">
                    <button type="submit" class="favorite-btn">
                        <i class="fa-solid fa-heart"></i>
                    </button>
                </form>
            </div>
        </a>
        <%
                }
            }
        %>
    </div>

    <div class="pm">
        <div>
            <a href="DeleteRecipeServlet"><i class="fa-solid fa-minus"></i></a>
<%--            <p>削除</p>--%>
        </div>
        <div>
            <a href="AddRecipeServlet"><i class="fa-solid fa-plus"></i></a>
<%--            <p>追加</p>--%>
        </div>
    </div>

    <footer>
        <div id="sp-fixed-menu" class="for-sp">
            <ul>
                <li>
                    <a href="CalendarServlet">
                        <i class="fa-solid fa-calendar-days"></i>
                        <p>家計簿</p>
                    </a>
                </li>
                <li>
                    <a href="SearchServlet">
                        <i class="fa-solid fa-utensils"></i>
                        <p>レシピ</p>
                    </a>
                </li>
                <li>
                    <a href="MemoListServlet">
                        <!-- <i class="fa-solid fa-pen-to-square"></i> -->
                        <i class="fa-solid fa-pen"></i>
                        <p>メモ</p>
                    </a>
                </li>
                <li>
                    <a href="SettingServlet">
                        <i class="fa-solid fa-gear"></i>
                        <p>設定</p>
                    </a>
                </li>
            </ul>
        </div>
    </footer>
</div>
</body>
</html>