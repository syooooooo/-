<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>メモ作成</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/memo.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="create">
<div class="body">
  <div class="head">
    <button class="back" type="button"  onclick="history.back()">
      <i class="fa-solid fa-circle-left"></i>
    </button>
    <div class="h2">
      <h2>メモを作成</h2>
    </div>

  </div>
  <form class="memoEdit" action="CreateMemoServlet" method="POST">
    <textarea name="title" rows="1" cols="30" placeholder="タイトルを入力"></textarea><br>
    <textarea name="content" rows="20" cols="30" placeholder="テキストを入力"></textarea><br>
    <div class="save">
      <button type="submit">保存</button>
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
