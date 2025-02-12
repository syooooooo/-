<%@ page import="utils.RecipeBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>レシピ追加</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="add">
<div class="body">
  <div class="head">
    <button class="back" type="button"  onclick="history.back()">
      <i class="fa-solid fa-circle-left"></i>
    </button>
    <div class="h2">
      <h2>レシピ情報を入力してください</h2>
    </div>
  </div>

  <form action="AddRecipeServlet" method="POST" enctype="multipart/form-data">

<%--    <div class="form-group custom-file-upload">--%>
<%--      <label for="image">画像</label>--%>
<%--      <label for="image" class="file-placeholder">ファイルを選択</label>--%>
<%--      <input type="file" id="image" name="image" class="file-input">--%>
<%--    </div>--%>
<%--    <div id="image-preview"></div>--%>

    <div class="form-group">
      <label for="name">レシピ名 <span style="color: red;">*</span></label>
      <input type="text" id="name" name="name" placeholder="例: カレーライス" required>
    </div>
    <div class="form-group">
      <label for="description">説明</label>
      <textarea id="description" name="description" placeholder="簡単な説明を入力してください"></textarea>
    </div>
    <div class="form-group">
      <label for="ingredients">材料 <span style="color: red;">*</span></label>
      <textarea id="ingredients" name="ingredients" placeholder="例: にんじん、玉ねぎ" required></textarea>
    </div>
    <div class="form-group">
      <label for="instructions">作り方 <span style="color: red;">*</span></label>
      <textarea id="instructions" name="instructions" placeholder="作り方を詳しく入力してください" required></textarea>
    </div>
    <div class="buttons">
      <button type="reset" class="reset">リセット</button>
      <button type="submit" class="submit">保存</button>
    </div>
  </form>

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
<script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input/dist/bs-custom-file-input.js"></script>
</body>
</html>
