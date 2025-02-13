<%--
  Created by IntelliJ IDEA.
  User: syoug
  Date: 2024/11/21
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="utils.UserBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新規登録</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
<div class="body">
    <div class="link">
        <a href="SigninServlet">ログイン</a>
    </div>

    <div class="h1">
        <h1 class="nr">新規登録</h1>
    </div>

    <form action="RegisterServlet" method="post">
        <div class="register">
            <div>
                <p>ID</p>
                <input type="text" name="id" size="20" required>
                <p>名前</p>
                <input type="text" name="name" size="20" required>
                <p>パスワード</p>
                <input type="password" name="password" size="20" required>
                <p>パスワード（確認）</p>
                <input type="password" name="confirmPassword" size="20" required>
            </div>
        </div>

        <div class="button">
            <button type="submit">確認画面へ</button>
        </div>
    </form>

</div>
</body>

</html>