<%@ page import="utils.RecipeBean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>レシピ編集</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/recipe.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="add">
<div class="body">
  <div class="head">
    <button class="back" type="button" onclick="history.back()">
      <i class="fa-solid fa-circle-left"></i>
    </button>
    <div class="h2">
      <h2>レシピを編集</h2>
    </div>
  </div>

  <form action="EditRecipeServlet" method="POST">
    <input type="hidden" name="id" id="id" value="${recipe.id}">
    <div class="form-group">
      <label for="name">レシピ名 <span style="color: red;">*</span></label>
      <input type="text" id="name" name="name" value="${recipe.name}" required>
    </div>
    <div class="form-group">
      <label for="description">説明</label>
      <textarea id="description" name="description">${recipe.description}</textarea>
    </div>
    <div class="form-group">
      <label for="ingredients">材料 <span style="color: red;">*</span></label>
      <textarea id="ingredients" name="ingredients" required>${recipe.ingredients}</textarea>
    </div>
    <div class="form-group">
      <label for="instructions">作り方 <span style="color: red;">*</span></label>
      <textarea id="instructions" name="instructions" required>${recipe.instructions}</textarea>
    </div>
    <div class="buttons">
      <button type="reset" class="reset">リセット</button>
      <button type="submit" class="submit">保存</button>
    </div>
  </form>
</div>

<script>
  document.querySelector("form").addEventListener("submit", function(event) {
    var nameField = document.getElementById("name");
    console.log("Name field value before submit: ", nameField.value);  // 追加: 値をコンソールに出力

    if (nameField.value.trim() === "") {
      alert("レシピ名は必須です");
      event.preventDefault();  // フォーム送信を防ぐ
    }
  });
</script>


<footer>
  <div id="sp-fixed-menu" class="for-sp">
    <ul>
      <li><a href="CalendarServlet"><i class="fa-solid fa-calendar-days"></i><p>家計簿</p></a></li>
      <li><a href="SearchServlet"><i class="fa-solid fa-utensils"></i><p>レシピ</p></a></li>
      <li><a href="MemoListServlet"><i class="fa-solid fa-pen"></i><p>メモ</p></a></li>
      <li><a href="SettingServlet"><i class="fa-solid fa-gear"></i><p>設定</p></a></li>
    </ul>
  </div>
</footer>
</body>
</html>
