<%@ page import="utils.RecipeBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>レシピ削除</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">

    <script type="text/javascript">
        function confirmDelete(event) {
            // ポップアップで確認メッセージを表示
            var result = confirm("このレシピを削除しますか？");

            // キャンセルを押した場合は送信をキャンセル
            if (!result) {
                event.preventDefault();
            }
        }
    </script>
</head>
<body class="delete">
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
        <h2>削除するレシピを選択してください</h2>
    </div>
    <div class="results">

        <%
            // レシピリストの取得とチェック
            List<RecipeBean> recipes = (List<RecipeBean>) request.getAttribute("recipes");
            if (recipes == null || recipes.isEmpty()) {
        %>
        <p>現在、表示できるレシピはありません。</p>
        <% } else {
            for (RecipeBean recipe : recipes) {
        %>
        <div class="recipe-card">
<%--            <img src="#" alt="レシピ画像">--%>
            <div class="recipe-details">
                <h3><%= recipe.getName() %></h3>
                <p>材料: <%= recipe.getIngredients() %></p>
            </div>
            <div class="recipe-actions">
                <form action="DeleteRecipeServlet" method="post" onsubmit="return confirm('本当に削除しますか？');">
                    <input type="hidden" name="userId" value="<%= session.getAttribute("user_id") %>">
                    <input type="hidden" name="recipeId" value="<%= recipe.getId() %>">
                    <button type="submit">
                        <i class="fa-solid fa-trash"></i>
                    </button>
                </form>
            </div>
        </div>
        <%
                }
            }
        %>
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
