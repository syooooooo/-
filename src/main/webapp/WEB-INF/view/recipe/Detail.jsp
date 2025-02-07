<%@ page import="utils.RecipeBean" %>
<%@ page import="utils.FavoriteDAO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>レシピ詳細</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="detail">
<div class="body">
    <div class="head">
        <button class="back" type="button" onclick="history.back()">
            <i class="fa-solid fa-circle-left"></i>
        </button>
        <div class="link">
            <a href="ListRecipeServlet">すべてのレシピ</a>
            <a href="FavoriteServlet">お気に入り</a>
        </div>
    </div>

    <div class="recipe-detail">
        <%
            // レシピの詳細情報を取得
            RecipeBean recipe = (RecipeBean) request.getAttribute("recipe");
            String userId = (String) session.getAttribute("user_id");
            String recipeClass = "unregistered";
            if (userId != null && FavoriteDAO.isFavorite(userId, recipe.getId())) {
                recipeClass = "registered";
            }
        %>
        <div class="recipe-card registered <%= recipeClass %>">
            <div class="recipe-details">
                <div class="h3b">
                    <h1><%= recipe.getName() %></h1>
                    <div class="recipe-actions">
                        <form action="FavoriteServlet" method="post">
                            <input type="hidden" name="userId" value="<%= session.getAttribute("user_id") %>">
                            <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">
                            <button type="submit" class="favorite-btn">
                                <i class="fa-solid fa-heart"></i>
                            </button>
                        </form>
                    </div>
                </div>
<%--                <div class="image">--%>
<%--                    <img src="" alt="レシピ画像">--%>
<%--                </div>--%>
                <div style="padding: 10px">
                    <p class="contents" style="border: none; padding: 0 15px"><%= recipe.getDescription().replace("\n", "<br>") %></p>
                </div>
                <div class="ingredients">
                    <p class="bold">材料</p>
                    <p class="contents"><%= recipe.getIngredients().replace("\n", "<br>") %></p>
                </div>
                <div class="instructions">
                    <p class="bold">作り方</p>
                    <p class="contents"><%= recipe.getInstructions().replace("\n", "<br>") %></p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="p">
    <div>
        <a href="EditRecipeServlet"><i class="fa-solid fa-pen"></i></a>
        <%--            <p>追加</p>--%>
    </div>
</div>
<footer>
    <div id="sp-fixed-menu" class="for-sp">
        <ul>
            <li>
                <a href="KakeiboServlet">
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
</body>
</html>
