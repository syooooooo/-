<%--
  Created by IntelliJ IDEA.
  User: syoug
  Date: 2024/11/21
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>レシピ</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
<div class="body">
  <div class="link">
    <a href="ListRecipeServlet">すべてのレシピ</a>
    <a href="FavoriteServlet">お気に入り</a>
  </div>

  <div class="h1">
    <h1>レシピをさがす</h1>
  </div>

  <form action="SearchServlet" method="Post" class="search-form-3">
    <label>
      <input type="text" id="keyword" name="keyword" placeholder="キーワードを入力">
    </label>
    <button type="submit" aria-label="検索"></button>
  </form>

  <div class="pm">
    <div>
      <a href="DeleteRecipeServlet"><i class="fa-solid fa-minus"></i></a>
<%--      <p>削除</p>--%>
    </div>
    <div>
      <a href="AddRecipeServlet"><i class="fa-solid fa-plus"></i></a>
<%--      <p>追加</p>--%>
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
