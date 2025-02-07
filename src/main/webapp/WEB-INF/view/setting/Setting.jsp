<%--
  Created by IntelliJ IDEA.
  User: syoug
  Date: 2024/11/21
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>設定</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/setting.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
<div class="body">
  <div class="h1">
    <h1>設定</h1>
  </div>

  <div class="list">
    <a href="ExpensetureServlet">定期支出</a>
    <a href="MoneyServlet">目標金額</a>
    <a href="ScheduleServlet">ゴミ出しスケジュール</a>
    <a class="logout" href="SignoutServlet">ログアウト</a>
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
