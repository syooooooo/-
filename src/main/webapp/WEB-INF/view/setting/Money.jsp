<%--
  Created by IntelliJ IDEA.
  User: syoug
  Date: 2025/01/28
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- スマホ対応 -->
    <title>目標金額</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/setting.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="money">
<div class="body container">
    <div class="head">
        <button class="back" type="button" onclick="location.href='SettingServlet'">
            <i class="fa-solid fa-circle-left"></i>
        </button>
        <h2>目標金額</h2>
    </div>
    <p>月の目標金額を設定してください</p>

    <!-- メッセージ表示 -->
    <c:if test="${not empty message}">
        <!-- <p style="color: green;">${message}</p> -->
    </c:if>

    <!-- 現在の目標金額 -->
    <div class="goal-display">
        現在の目標金額: <strong>${goalAmount}</strong> 円
    </div>

    <!-- 入力フォーム -->
    <form action="MoneyServlet" method="post">
        <div class="input-group">
            <input type="number" id="goalAmount" name="goalAmount" size="30" required placeholder="金額を入力">
            <span>円</span>
        </div>
        <div class="button">
            <button type="submit" class="update">更新</button>
        </div>
    </form>
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
</body>
</html>
