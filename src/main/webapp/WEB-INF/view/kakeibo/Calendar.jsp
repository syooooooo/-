<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Map" %>

<%
    // リクエストスコープからデータ取得
    List<String[]> calendarData = (List<String[]>) request.getAttribute("calendarData");
    int year = (int) request.getAttribute("year");
    int month = (int) request.getAttribute("month");

    // 今日の日付を取得
    Calendar today = Calendar.getInstance();
    int currentYear = today.get(Calendar.YEAR);
    int currentMonth = today.get(Calendar.MONTH) + 1;
    int currentDay = today.get(Calendar.DAY_OF_MONTH);

    // incomeByDay と expenseByDay をリクエストスコープから取得
    Map<Integer, Integer> incomeByDay = (Map<Integer, Integer>) request.getAttribute("incomeByDay");
    Map<Integer, Integer> expenseByDay = (Map<Integer, Integer>) request.getAttribute("expenseByDay");

    // リクエストスコープからゴミ出し情報を取得
    Map<Integer, String> garbageByDay = (Map<Integer, String>) request.getAttribute("garbageByDay");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>カレンダー</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/calendar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.0.0"></script>
</head>
<body>
<div class="calendar-wrap">
    <div class="calendar-header">
        <p class="name">名前: ${sessionScope.name}</p>
        <div class="selectbox">
            <select id="yearSelect" onchange="location.href='CalendarServlet?year=' + this.value + '&month=' + document.getElementById('monthSelect').value">
                <%
                    for (int i = year - 10; i <= year + 10; i++) {
                %>
                <option value="<%= i %>" <%= (i == year) ? "selected" : "" %>><%= i %>年</option>
                <%
                    }
                %>
            </select>
            <select id="monthSelect" onchange="location.href='CalendarServlet?year=' + document.getElementById('yearSelect').value + '&month=' + this.value">
                <%
                    for (int i = 1; i <= 12; i++) {
                %>
                <option value="<%= i %>" <%= (i == month) ? "selected" : "" %>><%= i %>月</option>
                <%
                    }
                %>
            </select>
        </div>
    </div>
    <div class="ym">
        <button onclick="changeMonth(-1)">&#9664;</button>
        <span id="monthYear"><%= year %>年 <%= month %>月</span>
        <button onclick="changeMonth(1)">&#9654;</button>
    </div>
    <table class="calendar">
        <thead>
        <tr>
            <th class="sun">日</th>
            <th>月</th>
            <th>火</th>
            <th>水</th>
            <th>木</th>
            <th>金</th>
            <th class="sat">土</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (String[] week : calendarData) {
        %>
        <tr>
            <%
                int dayOfWeekIndex = 0; // 曜日のインデックス
                for (String day : week) {
                    if (day == null || day.isEmpty()) {
            %>
            <td class="empty"></td>
            <%
            } else if (year == currentYear && month == currentMonth && day.equals(String.valueOf(currentDay))) {
            %>
            <td class="today">
                <a href="KakeiboServlet?year=<%= year %>&month=<%= month %>&day=<%= day %>">
                    <div class="td-head">
                        <p class="day"><%= day %></p>
                        <p class="garbage"><%= garbageByDay.getOrDefault(Integer.parseInt(day), "") %></p>
                    </div>
                    <p class="blue">
                        <%= incomeByDay.get(Integer.parseInt(day)) != null ? incomeByDay.get(Integer.parseInt(day)) : "" %>
                    </p>
                    <p class="red">
                        <%= expenseByDay.get(Integer.parseInt(day)) != null ? expenseByDay.get(Integer.parseInt(day)) : "" %>
                    </p>
                </a>
            </td>
            <%
            } else {
                String dayClass = "";
                if (dayOfWeekIndex == 0) { // 日曜日
                    dayClass = "sun";
                } else if (dayOfWeekIndex == 6) { // 土曜日
                    dayClass = "sat";
                }
            %>
            <td class="<%= dayClass %>">
                <a href="KakeiboServlet?year=<%= year %>&month=<%= month %>&day=<%= day %>">
                    <div class="td-head">
                        <p class="day"><%= day %></p>
                        <p class="garbage"><%= garbageByDay.getOrDefault(Integer.parseInt(day), "") %></p>
                    </div>
                    <p class="blue">
                        <span class="display" data="<%= incomeByDay.get(Integer.parseInt(day)) != null
                                ? String.valueOf(incomeByDay.get(Integer.parseInt(day))).length() > 6
                                ? String.valueOf(incomeByDay.get(Integer.parseInt(day))).substring(0, 5) + "…"
                                : incomeByDay.get(Integer.parseInt(day))
                                : "" %>"></span>
                    </p>
                    <p class="red">
                        <span class="display" data="<%= expenseByDay.get(Integer.parseInt(day)) != null
                                ? String.valueOf(expenseByDay.get(Integer.parseInt(day))).length() > 5
                                ? String.valueOf(expenseByDay.get(Integer.parseInt(day))).substring(0, 5) + "…"
                                : expenseByDay.get(Integer.parseInt(day))
                                : "" %>"></span>

                    </p>
                </a>
            </td>
            <%
                    }
                    dayOfWeekIndex++; // 曜日のインデックスを進める
                }
            %>
        </tr>
        <% } %>
        </tbody>
    </table>

    <%
        // リクエストスコープから収入合計と支出合計を取得
        Integer totalIncomeByMonth = (Integer) request.getAttribute("totalIncomeByMonth");
        Integer totalExpenseByMonth = (Integer) request.getAttribute("totalExpenseByMonth");

        // もし、どちらかがnullの場合は0を代入
        if (totalIncomeByMonth == null) totalIncomeByMonth = 0;
        if (totalExpenseByMonth == null) totalExpenseByMonth = 0;

        // 差額の計算
        int balance = totalIncomeByMonth - totalExpenseByMonth;
    %>

    <div class="syuusi">
        <div>
            <h4 style="color: #4444aa">収入合計</h4>
            <p style="color: #4444aa"><%= totalIncomeByMonth %> 円</p>
        </div>
        <div>
            <h4 style="color: #aa4444">支出合計</h4>
            <p style="color: #aa4444"><%= totalExpenseByMonth %> 円</p>
        </div>
        <div>
            <h4 style="color: #555">差額</h4>
            <p style="color: #555"><%= balance %> 円</p>
        </div>
    </div>

    <div class="gg">
        <div>
            <a href="javascript:void(0)"><i class="fa-solid fa-chart-simple"></i></a>
<%--            <p>グラフ</p>--%>
        </div>
        <div>
            <a href="ScheduleServlet"><i class="fa-solid fa-sack-xmark"></i></a>
<%--            <p>ゴミの日</p>--%>
        </div>
    </div>


    <!-- モーダル -->
    <div id="graphModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2><%= month %>月の収支</h2>
            <canvas id="incomeExpenseChart"></canvas>
            <%
                String incomeByGenre = (String) request.getAttribute("incomeByGenre");
                String expenseByGenre = (String) request.getAttribute("expenseByGenre");
                int income1 = (request.getAttribute("income1") != null) ? (int) request.getAttribute("income1") : 0;
                int income2 = (request.getAttribute("income2") != null) ? (int) request.getAttribute("income2") : 0;
                int income3 = (request.getAttribute("income3") != null) ? (int) request.getAttribute("income3") : 0;
                int income4 = (request.getAttribute("income4") != null) ? (int) request.getAttribute("income4") : 0;
                int income5 = (request.getAttribute("income5") != null) ? (int) request.getAttribute("income5") : 0;
                int expense1 = (request.getAttribute("expense1") != null) ? (int) request.getAttribute("expense1") : 0;
                int expense2 = (request.getAttribute("expense2") != null) ? (int) request.getAttribute("expense2") : 0;
                int expense3 = (request.getAttribute("expense3") != null) ? (int) request.getAttribute("expense3") : 0;
                int expense4 = (request.getAttribute("expense4") != null) ? (int) request.getAttribute("expense4") : 0;
                int expense5 = (request.getAttribute("expense5") != null) ? (int) request.getAttribute("expense5") : 0;
            %>
            <h2>収入グラフ</h2>
            <div>
                <canvas id="incomePieChart"></canvas>
                <div id="income-details">
                    <table>
                        <tr>
                            <th>給料</th>
                            <td><%= income1 %> 円</td>
                        </tr>
                        <tr>
                            <th>副業</th>
                            <td><%= income2 %> 円</td>
                        </tr>
                        <tr>
                            <th>投資</th>
                            <td><%= income3 %> 円</td>
                        </tr>
                        <tr>
                            <th>臨時収入</th>
                            <td><%= income4 %> 円</td>
                        </tr>
                        <tr>
                            <th>その他</th>
                            <td><%= income5 %> 円</td>
                        </tr>
                        <tr>
                            <th class="sum">合計</th>
                            <td class="sum"><%= totalIncomeByMonth %> 円</td>
                        </tr>
                    </table>
                </div>
            </div>
            <h2 style="margin-top: 50px">支出グラフ</h2>
            <div>
                <canvas id="expensePieChart"></canvas>
                <div id="expense-details">
                    <table>
                        <tr>
                            <th>生活費</th>
                            <td><%= expense1 %> 円</td>
                        </tr>
                        <tr>
                            <th>娯楽費</th>
                            <td><%= expense2 %> 円</td>
                        </tr>
                        <tr>
                            <th>交通費</th>
                            <td><%= expense3 %> 円</td>
                        </tr>
                        <tr>
                            <th>医療費</th>
                            <td><%= expense4 %> 円</td>
                        </tr>
                        <tr>
                            <th>その他</th>
                            <td><%= expense5 %> 円</td>
                        </tr>
                        <tr>
                            <th class="sum">合計</th>
                            <td class="sum"><%= totalExpenseByMonth %> 円</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <style>
        @media screen and (min-width: 500px) {
            /* モーダルのスタイル */
            .modal {
                display: none; /* 最初は非表示 */
                position: fixed;
                /*z-index: 1;*/
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.4);
                /*padding-top: 60px;*/
            }

            .modal-content {
                background-color: #fff;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
                height: 75vh;
                overflow-y: auto;
            }

            .modal-content h2{
                margin: 20px;
                font-size: 20px;
                color: #555;
            }

            .modal-content #incomeExpenseChart{
                margin-bottom: 50px;
            }

            #incomePieChart {
                width: 30vw;
                /*height: 30vw;*/
            }

            #expensePieChart {
                width: 30vw;
                /*height: 30vw;*/
            }

            #income-details, #expense-details {
                margin-top: 20px;
                font-size: 14px;
                color: #333;
                text-align: left;
            }

            #income-details p, #expense-details p {
                margin: 5px 0;
            }


            .close {
                color: #aaa;
                font-size: 28px;
                font-weight: bold;
                /*position: absolute;*/
                /*top: 85px;*/
                /*right: 30px;*/
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }

            #expense-details table, #income-details table{
                width: 90%;
            }

            #expense-details th, #income-details th{
                width: 30%;
                text-align: left;
                color: #555;
            }

            #expense-details td, #income-details td{
                width: 70%;
                text-align: right;
            }

            .sum{
                padding: 5% 0;
                font-size: 20px;
            }
        }



        @media screen and (max-width: 499px) {
            /* モーダルのスタイル */
            .modal {
                display: none; /* 最初は非表示 */
                position: fixed;
                /*z-index: 1;*/
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.4);
                padding-top: 60px;
            }

            .modal-content {
                background-color: #fff;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
                height: 75vh;
                overflow-y: auto;
            }

            .modal-content h2{
                margin: 20px;
                font-size: 20px;
                color: #555;
            }

            .modal-content #incomeExpenseChart{
                margin-bottom: 50px;
            }

            #incomePieChart {
                margin: auto;
                max-width: 250px;
                /*height: 30vw;*/
            }

            #expensePieChart {
                margin: auto;
                max-width: 250px;
                /*height: 30vw;*/
            }

            #income-details, #expense-details {
                margin-top: 20px;
                font-size: 14px;
                color: #333;
                text-align: left;
            }

            #income-details p, #expense-details p {
                margin: 5px 0;
            }


            .close {
                color: #aaa;
                font-size: 28px;
                font-weight: bold;
                /*position: absolute;*/
                /*top: 85px;*/
                /*right: 30px;*/
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }

            #expense-details table, #income-details table{
                width: 90%;
            }

            #expense-details th, #income-details th{
                width: 30%;
                text-align: left;
                color: #555;
            }

            #expense-details td, #income-details td{
                width: 70%;
                text-align: right;
            }

            .sum{
                padding: 5% 0;
                font-size: 20px;
            }
        }
    </style>
    <script>
        // モーダルを開くためのボタン
        document.querySelector('.fa-chart-simple').addEventListener('click', function () {
            var modal = document.getElementById('graphModal');
            modal.style.display = "block";

            // Chart.jsでグラフを描画
            var ctx = document.getElementById('incomeExpenseChart').getContext('2d');

            // もし既にグラフが描画されている場合は、古いグラフを破棄
            if (window.myChart) {
                window.myChart.destroy();
            }

            window.myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['収入', '支出'],
                    datasets: [{
                        data: [<%= totalIncomeByMonth %>, <%= totalExpenseByMonth %>], // データをJavaから渡す
                        backgroundColor: ['#8888ff', '#Ff8888'],
                        borderColor: ['#8888ff', '#Ff8888'],
                        borderWidth: 1,
                        barThickness: 30
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: false // ラベルを非表示にする
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

        });

        <%--var incomeData = ${incomeByGenre};  // サーバーから渡されたジャンルごとのデータをJavaScriptに渡す--%>
        var incomeData = JSON.parse('<%= incomeByGenre %>');

        var labels = ['給料', '副業', '投資', '臨時収入', 'その他'];
        var data = [<%= income1 %>, <%= income2 %>, <%= income3 %>, <%= income4 %>, <%= income5 %>];

        // for (var genre in incomeData) {
        //     labels.push(genre);  // ジャンル名をラベルに追加
        //     data.push(incomeData[genre]);  // 各ジャンルの収入合計をデータに追加
        // }

        var ctx = document.getElementById('incomePieChart').getContext('2d');
        var incomePieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: '収入のジャンルごとの割合',
                    data: data,
                    backgroundColor: ['#FF8888', '#FFFF88', '#88FF88', '#88FFFF', '#8888FF'],
                    borderColor: ['#FF8888', '#FFFF88', '#88FF88', '#88FFFF', '#8888FF'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ¥' + tooltipItem.raw.toLocaleString();
                            }
                        }
                    },
                    // datalabels プラグインの設定
                    datalabels: {
                        color: '#fff',
                        formatter: function(value, context) {
                            var total = <%= totalIncomeByMonth %>;
                            var percentage = (value / total * 100).toFixed(1); // パーセンテージ計算
                            return percentage + '%'; // パーセンテージを表示
                        },
                        font: {
                            weight: 'bold',
                            size: 14
                        },
                        align: 'center',
                        anchor: 'center'
                    }
                }
            }
        });

        // 支出の円グラフ
        var expenseData = JSON.parse('<%= expenseByGenre %>');

        var labels = ['生活費', '娯楽費', '交通費', '医療費', 'その他'];
        var data = [<%= expense1 %>, <%= expense2 %>, <%= expense3 %>, <%= expense4 %>, <%= expense5 %>];

        // for (var genre in expenseData) {
        //     expenseLabels.push(genre);
        //     expenseDataArray.push(expenseData[genre]);
        // }

        var expenseCtx = document.getElementById('expensePieChart').getContext('2d');
        var expensePieChart = new Chart(expenseCtx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: '支出のジャンルごとの割合',
                    data: data,
                    backgroundColor: ['#FF8888', '#FFFF88', '#88FF88', '#88FFFF', '#8888FF'],
                    borderColor: ['#FF8888', '#FFFF88', '#88FF88', '#88FFFF', '#8888FF'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ¥' + tooltipItem.raw.toLocaleString();
                            }
                        }
                    }
                }
            }
        });

        // モーダルを閉じる
        document.querySelector('.close').addEventListener('click', function () {
            var modal = document.getElementById('graphModal');
            modal.style.display = "none";
        });

        // モーダル外をクリックして閉じる
        window.addEventListener('click', function (event) {
            var modal = document.getElementById('graphModal');
            if (event.target == modal) {
                modal.style.display = "none";
            }
        });
    </script>


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

<script>
    function changeMonth(offset) {
        let yearSelect = document.getElementById('yearSelect');
        let monthSelect = document.getElementById('monthSelect');
        let year = parseInt(yearSelect.value);
        let month = parseInt(monthSelect.value) + offset;
        if (month < 1) {
            month = 12;
            year--;
        } else if (month > 12) {
            month = 1;
            year++;
        }
        location.href = 'CalendarServlet?year=' + year + '&month=' + month;
    }
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let isDesktop = window.innerWidth > 500; // 画面幅768px以上をデスクトップと判定
        let maxLength = isDesktop ? 10 : 5; // デスクトップは10文字、スマホは5文字

        document.querySelectorAll(".display").forEach(span => {
            let income = span.getAttribute("data");
            if (income.length > maxLength) {
                span.textContent = income.substring(0, maxLength) + "…";
            } else {
                span.textContent = income;
            }
        });
    });
</script>
</body>
</html>
