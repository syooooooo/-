<%@ page import="utils.UserBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ログイン</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">

</head>

<body>
<div class="body">
  <div class="link">
    <a href="RegisterServlet">新規登録</a>
  </div>

  <div class="h1">
    <h1>ログイン</h1>
  </div>

  <!-- フォームの開始 -->
  <form action="SigninServlet" method="post">
    <div class="login">
      <div>
        <p>ID</p>
        <input class="id" type="text" name="id" size="30" required>
        <p>パスワード</p>
        <input class="password" type="password" name="password" size="30" required>
      </div>
    </div>

    <div class="button">
      <button type="submit">ログイン</button>
    </div>
  </form>
  <!-- フォームの終了 -->
</div>
</body>

</html>
