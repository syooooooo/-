<%@ page import="utils.UserBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登録内容確認</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="confirmregister">
<div class="body">
    <div class="h1">
        <h2 class="cr">以下の内容で登録しますか？</h2>
    </div>
    <form action="RegisterServlet" method="POST">
        <div class="confirm">
            <table>
                <tr>
                    <th><p>ID</p>  <!-- userオブジェクトからidを取得 --></th>
                    <td><p>${user.id}</p></td>
                </tr>
                <tr>
                    <th><p>名前</p></th>
                    <td><p>${user.name}</p>  <!-- userオブジェクトからnameを取得 --></td>
                </tr>
            </table>
        </div>
        <div class="button">
            <button type="submit" name="action" value="register">登録する</button>
        </div>
        <div class="fix">
            <button type="button" onclick="history.back()">修正する</button>
<%--            <a href="RegisterServlet">修正する</a>--%>
        </div>
    </form>
</div>
</body>
</html>
