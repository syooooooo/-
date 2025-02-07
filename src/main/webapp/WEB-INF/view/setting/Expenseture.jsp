<%@ page import="java.util.Map" %>
<%@ page import="utils.ExpensetureBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>定期支出</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/setting.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="ture">
<div class="body">
    <div class="head">
        <button class="back" type="button" onclick="history.back()">
            <i class="fa-solid fa-circle-left"></i>
        </button>
        <h2>定期支出</h2>
    </div>



    <!-- 入力フォーム -->
    <div class="form-container">
        <form action="ExpensetureServlet" method="post">
            <div class="td">
                <input class="title" type="text" name="title" placeholder="タイトル" required>
                <input class="date" type="text" name="date" required placeholder="日付" id="dateInput">
                <script>
                    document.getElementById('dateInput').addEventListener('input', function() {
                        const value = this.value;
                        if (value < 1 || value > 31) {
                            alert("1から31の数字を入力してください");
                        }
                    });
                </script>
            </div>
            <div class="ag">
                <input class="amount" type="number" name="amount" placeholder="金額" step="1" required>
                <select class="genre" name="genre" required>
                    <option value="生活費">生活費</option>
                    <option value="娯楽費">娯楽費</option>
                    <option value="交通費">交通費</option>
                    <option value="医療費">医療費</option>
                    <option value="その他">その他</option>
                </select>
                <button type="submit">追加</button>
            </div>
        </form>
    </div>

    <!-- 登録データ表示 -->
    <div class="scroll-container">
        <%
            // グループ化されたデータを取得
            Map<Integer, List<ExpensetureBean>> groupedByDay = (Map<Integer, List<ExpensetureBean>>) request.getAttribute("groupedByDay");

            // 日付順にソートされたデータを処理
            if (groupedByDay != null && !groupedByDay.isEmpty()) {
                for (Map.Entry<Integer, List<ExpensetureBean>> entry : groupedByDay.entrySet()) {
                    Integer day = entry.getKey();  // 日付
                    List<ExpensetureBean> expenses = entry.getValue();  // その日の支出リスト
        %>
        <!-- 日付ごとのヘッダー -->
        <h3><%= day %>日</h3>
        <table>
            <thead>
            <tr>
                <th class="g">ジャンル</th>
                <th class="t">タイトル</th>
                <th class="a">金額</th>
                <th class="d"></th>
            </tr>
            </thead>
            <tbody>
            <%
                for (ExpensetureBean expense : expenses) {
            %>
            <tr>
                <td><%= expense.getGenre() %></td>
                <td><%= expense.getTitle() %></td>
                <td><%= expense.getAmount() %> 円</td>
                <td style="text-align: center">
                    <form action="ExpensetureServlet" method="post" onsubmit="return confirm('本当に削除しますか？');">
                        <input type="hidden" name="id" value="<%= expense.getId() %>">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit"><i class="fa-solid fa-trash"></i></button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <%
            }
        } else {
        %>
        <p style="text-align: center;">データがありません</p>
        <%
            }
        %>
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
</body>
</html>