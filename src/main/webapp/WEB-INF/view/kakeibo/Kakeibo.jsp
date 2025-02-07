<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${year}年 ${month}月 ${day}日 の家計簿</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/calendar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="kakeibo">
<div class="body">
    <button class="back" type="button"  onclick="location.href='CalendarServlet'">
        <i class="fa-solid fa-circle-left"></i>
    </button>

    <form action="KakeiboServlet" method="get" id="dateForm">
        <input type="hidden" name="year" value="${year}">
        <input type="hidden" name="month" value="${month}">
        <input type="hidden" name="day" value="${day}">
        <button type="button" id="previousDayButton">&#9664;</button>
        <span>${year}年 ${month}月 ${day}日</span>
        <button type="button" id="nextDayButton">&#9654;</button>
    </form>

    <script>
        document.getElementById('previousDayButton').addEventListener('click', function() {
            var year = parseInt(document.querySelector('input[name="year"]').value);
            var month = parseInt(document.querySelector('input[name="month"]').value);
            var day = parseInt(document.querySelector('input[name="day"]').value);

            var currentDate = new Date(year, month - 1, day);
            currentDate.setDate(currentDate.getDate() - 1);

            document.querySelector('input[name="year"]').value = currentDate.getFullYear();
            document.querySelector('input[name="month"]').value = currentDate.getMonth() + 1;
            document.querySelector('input[name="day"]').value = currentDate.getDate();

            document.getElementById('dateForm').submit();
        });

        document.getElementById('nextDayButton').addEventListener('click', function() {
            var year = parseInt(document.querySelector('input[name="year"]').value);
            var month = parseInt(document.querySelector('input[name="month"]').value);
            var day = parseInt(document.querySelector('input[name="day"]').value);

            var currentDate = new Date(year, month - 1, day);
            currentDate.setDate(currentDate.getDate() + 1);

            document.querySelector('input[name="year"]').value = currentDate.getFullYear();
            document.querySelector('input[name="month"]').value = currentDate.getMonth() + 1;
            document.querySelector('input[name="day"]').value = currentDate.getDate();

            document.getElementById('dateForm').submit();
        });
    </script>

    <div class="ss">
        <h2 style="color: #4444aa">収入</h2>
        <table>
            <tr>
                <th class="category">ジャンル</th>
                <th class="title">タイトル</th>
                <th class="money">金額</th>
                <th class="delete"></th>
            </tr>
            <c:if test="${empty incomeList}">
                <tr>
                    <td colspan="4" class="no-data-message">
                        <p>収入データはありません。</p>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="income" items="${incomeList}">
                <tr class="income">
                    <td>
                        <a href="#" class="edit-link" data-id="${income.id}" data-genre="${income.genre}" data-title="${income.title}" data-amount="${income.amount}" data-type="income">${income.genre}</a>
                    </td>
                    <td>
                        <a href="#" class="edit-link" data-id="${income.id}" data-genre="${income.genre}" data-title="${income.title}" data-amount="${income.amount}" data-type="income">${income.title}</a>
                    </td>
                    <td>
                        <a href="#" class="edit-link" data-id="${income.id}" data-genre="${income.genre}" data-title="${income.title}" data-amount="${income.amount}" data-type="income">${income.amount} 円</a>
                    </td>
                    <td style="text-align: center">
                        <form action="/sotukenn-1.0-SNAPSHOT/DeleteKakeiboServlet" method="post" onsubmit="return confirm('本当に削除しますか？');">
                            <input type="hidden" name="year" value="${year}">
                            <input type="hidden" name="month" value="${month}">
                            <input type="hidden" name="day" value="${day}">
                            <input type="hidden" name="id" value="${income.id}">
                            <input type="hidden" name="data-type" value="income">
                            <button type="submit"><i class="fa-solid fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <h2 style="color: #aa4444">支出</h2>
        <table>
            <tr>
                <th class="category">ジャンル</th>
                <th class="title">タイトル</th>
                <th class="money">金額</th>
                <th class="delete"></th>
            </tr>
            <c:if test="${empty expenseList}">
                <tr>
                    <td colspan="4" class="no-data-message">
                        <p>支出データはありません。</p>
                    </td>
                </tr>
            </c:if>
            <c:forEach var="expense" items="${expenseList}">
                <tr class="expense">
                    <td>
                        <a href="#" class="edit-link" data-id="${expense.id}" data-genre="${expense.genre}" data-title="${expense.title}" data-amount="${expense.amount}" data-type="expense">${expense.genre}</a>
                    </td>
                    <td>
                        <a href="#" class="edit-link" data-id="${expense.id}" data-genre="${expense.genre}" data-title="${expense.title}" data-amount="${expense.amount}" data-type="expense">${expense.title}</a>
                    </td>
                    <td>
                        <a href="#" class="edit-link" data-id="${expense.id}" data-genre="${expense.genre}" data-title="${expense.title}" data-amount="${expense.amount}" data-type="expense">${expense.amount} 円</a>
                    </td>
                    <td>
                        <form action="/sotukenn-1.0-SNAPSHOT/DeleteKakeiboServlet" method="post" onsubmit="return confirm('本当に削除しますか？');">
                            <input type="hidden" name="year" value="${year}">
                            <input type="hidden" name="month" value="${month}">
                            <input type="hidden" name="day" value="${day}">
                            <input type="hidden" name="id" value="${expense.id}">
                            <input type="hidden" name="data-type" value="expense">
                            <button type="submit"><i class="fa-solid fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="p">
        <div>
            <a href="#" id="openModalButton"><i class="fa-solid fa-plus"></i></a>
<%--            <p>追加</p>--%>
        </div>
    </div>

    <div id="modal" class="modal">
        <div class="modal-content">
            <span id="closeModalButton" class="close">&times;</span>
            <h2>新しいデータを登録</h2>
            <form action="KakeiboServlet" method="post">
                <input type="hidden" name="year" value="${year}">
                <input type="hidden" name="month" value="${month}">
                <input type="hidden" name="day" value="${day}">


                <label for="type">タイプ</label>:
                <input type="radio" id="income" name="type" value="income" onchange="updateGenreOptions()" required> 収入
                <input type="radio" id="expense" name="type" value="expense" onchange="updateGenreOptions()" required> 支出<br>

                <label for="genre">ジャンル</label>:
                <select id="genre" name="genre" required></select><br>

                <label for="title">タイトル</label>:
                <input type="text" id="title" name="title" required><br>

                <label for="amount">金額</label>:
                <input type="number" id="amount" name="amount" required><br>


                <div class="kakeibo-submit">
                    <button type="submit">登録</button>
                </div>
            </form>
        </div>
    </div>

    <div id="editModal" class="modal">
        <div class="modal-content">
            <span id="closeEditModalButton" class="close">&times;</span>
            <h2>データを編集</h2>
            <form action="EditKakeiboServlet" method="post">
                <input type="hidden" name="id" value="${editId}">
                <input type="hidden" name="year" value="${year}">
                <input type="hidden" name="month" value="${month}">
                <input type="hidden" name="day" value="${day}">

                <label for="editType">タイプ</label>:
                <input type="radio" id="editIncome" name="editType" value="income" onchange="updateGenreOptions()" required> 収入
                <input type="radio" id="editExpense" name="editType" value="expense" onchange="updateGenreOptions()" required> 支出<br>

                <label for="editGenre">ジャンル</label>:
                <select id="editGenre" name="genre" required></select><br>

                <label for="editTitle">タイトル</label>:
                <input type="text" id="editTitle" name="title" value="${editTitle}" required><br>

                <label for="editAmount">金額</label>:
                <input type="number" id="editAmount" name="amount" value="${editAmount}" required><br>

                <div class="kakeibo-submit">
                    <button type="submit">編集</button>
                </div>
            </form>
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

    <script>
        function updateGenreOptions() {
            console.log("updateGenreOptions called");
            var genreSelects = [
                document.getElementById("genre"),
                document.getElementById("editGenre")
            ];

            var type = document.querySelector('input[name="type"]:checked') || document.querySelector('input[name="editType"]:checked');

            if (!type) return;

            var options = [];
            if (type.value === "income") {
                options = ["給料", "副業", "投資", "臨時収入", "その他"];
            } else if (type.value === "expense") {
                options = ["生活費", "娯楽費", "交通費", "医療費", "その他"];
            }

            genreSelects.forEach(function (genreSelect) {
                if (genreSelect) {
                    genreSelect.innerHTML = "";
                    options.forEach(function (option) {
                        var optionElement = document.createElement("option");
                        optionElement.value = option;
                        optionElement.textContent = option;
                        genreSelect.appendChild(optionElement);
                    });
                }
            });
        }
    </script>

    <script>
        // モーダルを開く処理
        document.getElementById('openModalButton').addEventListener('click', function () {
            document.getElementById('modal').style.display = 'block';
        });

        // モーダルを閉じる処理
        document.getElementById('closeModalButton').addEventListener('click', function () {
            document.getElementById('modal').style.display = 'none';
        });

        // 編集モーダルを開く処理
        document.querySelectorAll('.edit-link').forEach(function (link) {
            link.addEventListener('click', function (e) {
                e.preventDefault();
                var id = this.getAttribute('data-id');
                var genre = this.getAttribute('data-genre');
                var title = this.getAttribute('data-title');
                var amount = this.getAttribute('data-amount');
                var type = this.getAttribute('data-type');

                document.querySelector('#editModal input[name="id"]').value = id;
                document.querySelector('#editModal input[name="title"]').value = title;
                document.querySelector('#editModal input[name="amount"]').value = amount;

                document.querySelector('input[name="editType"][value="' + type + '"]').checked = true;
                updateGenreOptions(); // 編集時にもジャンルリストを更新

                document.getElementById('editModal').style.display = 'block';
            });
        });

        document.getElementById('closeEditModalButton').addEventListener('click', function () {
            document.getElementById('editModal').style.display = 'none';
        });

        // モーダル外をクリックした場合にモーダルを閉じる
        window.addEventListener('click', function(event) {
            var modal = document.getElementById('modal');
            var editModal = document.getElementById('editModal');

            // モーダル外部をクリックした場合
            if (event.target === modal || event.target === editModal) {
                modal.style.display = 'none'; // モーダルを非表示
                editModal.style.display = 'none'; // 編集モーダルを非表示
            }
        });

        // モーダルコンテンツ内部をクリックしてもモーダルが閉じないようにする
        document.querySelector('.modal-content').addEventListener('click', function(event) {
            event.stopPropagation(); // モーダル外部へのクリックを無視
        });

    </script>
</div>
</body>
</html>
