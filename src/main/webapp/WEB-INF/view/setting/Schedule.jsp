<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- スマホ対応 -->
    <title>ゴミ出しスケジュール</title>
    <style>
        @media screen and (min-width: 500px) {
            body {
                font-family: Arial, sans-serif;
                background-color: #fff;
                color: #333;
                /*margin: 0;*/
                padding: 0;
                /*display: flex;*/
                flex-direction: column;
                align-items: center;
            }

            .body{
                width: 100%;
            }

            /*.head {*/
            /*    display: flex;*/
            /*    margin-bottom: 30px;*/
            /*}*/

            /*.back {*/
            /*    width: 30px;*/
            /*    height: 30px;*/
            /*    margin-top: 0;*/
            /*    margin-bottom: 0;*/
            /*    margin-left: 10px;*/
            /*    margin-right: 20px;*/
            /*    padding: 1px 6px;*/
            /*    background-color: #fff;*/
            /*    border: none;*/
            /*    align-self: center;*/
            /*}*/

            .back i {
                font-size: 30px;
            }

            /*h2 {*/
            /*    width: 295%;*/
            /*    height: 30px;*/
            /*    margin: 0;*/
            /*    align-self: center;*/
            /*    !*padding-top: 5px;*!*/
            /*    !*padding-bottom: 5px;*!*/
            /*    !*font-size: 20px;*!*/
            /*    font-weight: bold;*/
            /*    line-height: unset;*/
            /*    color: #555555;*/
            /*}*/
            .head{
                display: flex;
                margin-bottom: 30px;
            }
            .head .back{
                width: 30px;
                height: 30px;
                margin-top: 0;
                margin-bottom: 0;
                margin-left: 10px;
                margin-right: 20px;
                padding: 1px 6px;
                background-color: #fff;
                border: none;
            }

            .head h2{
                margin: 0;
                /*padding-top: 5px;*/
                /*padding-bottom: 5px;*/
                /*font-size: 20px;*/
                font-weight: bold;
                line-height: unset;
                color: #555555;
            }




            .result{
                height: 70vh;
                overflow-y: auto;
            }

            .result ul{
                width: 90%;
                margin: auto;
                padding: 0;
            }

            .result li{
                width: 40%;
                display: flex;
                margin: 30px auto;
                padding: 10px;
                list-style: none;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .result div{
                width: 85%;
            }

            .result form{
                width: 10%;
                margin: 0;
                margin-left: 5%;
                align-self: center;
            }
            .result button{
                padding: 10px;
                text-align: right;
                background-color: #fff;
                border: none;
            }
            .result button i{
                font-size: 16px;
            }

            .gomi {
                width: 90%;
                max-width: 400px;
                background-color: #f9f9f9;
                margin: auto;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px; /* ボタンとフォームが重ならないよう余白を確保 */
                box-sizing: border-box;
                height: 65vh;
                overflow-y: auto;
            }

            label {
                font-size: 1em;
                margin-bottom: 5px;
                display: block;
            }

            input[type="text"] {
                width: 100%;
                padding: 5px;
                margin-top: 5px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 1em;
                box-sizing: border-box;
            }

            .radio-group, .checkbox-group {
                margin-bottom: 15px;
            }

            .checkbox-group label, .radio-group label {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                cursor: pointer;
            }

            input[type="radio"], input[type="checkbox"] {
                margin-right: 10px;
                cursor: pointer;
            }

            .button-group {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap; /* スマホ対応のためボタンを折り返し可能に */
            }

            button {
                padding: 10px 20px;
                font-size: 1em;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
                /*flex: 1 1 calc(50% - 10px); !* スマホ対応：ボタンを半分の幅で表示 *!*/
                margin: 5px;
            }

            button:hover {
                transform: scale(1.05);
            }

            .btn-back {
                background-color: #FF6666;
                color: #fff;
            }

            .btn-submit {
                background-color: #66aa66;
                color: #fff;
            }
        }





        @media screen and (max-width: 499px) {
            body {
                font-family: Arial, sans-serif;
                background-color: #fff;
                color: #333;
                /*margin: 0;*/
                padding: 0;
                /*display: flex;*/
                flex-direction: column;
                align-items: center;
            }

            .body{
                width: 100%;
            }

            /*.head {*/
            /*    display: flex;*/
            /*    margin-bottom: 30px;*/
            /*}*/

            /*.back {*/
            /*    width: 30px;*/
            /*    height: 30px;*/
            /*    margin-top: 0;*/
            /*    margin-bottom: 0;*/
            /*    margin-left: 10px;*/
            /*    margin-right: 20px;*/
            /*    padding: 1px 6px;*/
            /*    background-color: #fff;*/
            /*    border: none;*/
            /*    align-self: center;*/
            /*}*/

            .back i {
                font-size: 30px;
            }

            /*h2 {*/
            /*    width: 295%;*/
            /*    height: 30px;*/
            /*    margin: 0;*/
            /*    align-self: center;*/
            /*    !*padding-top: 5px;*!*/
            /*    !*padding-bottom: 5px;*!*/
            /*    !*font-size: 20px;*!*/
            /*    font-weight: bold;*/
            /*    line-height: unset;*/
            /*    color: #555555;*/
            /*}*/
            .head{
                display: flex;
                margin-bottom: 30px;
            }
            .head .back{
                width: 30px;
                height: 30px;
                margin-top: 0;
                margin-bottom: 0;
                margin-left: 10px;
                margin-right: 20px;
                padding: 1px 6px;
                background-color: #fff;
                border: none;
            }

            .head h2{
                margin: 0;
                /*padding-top: 5px;*/
                /*padding-bottom: 5px;*/
                /*font-size: 20px;*/
                font-weight: bold;
                line-height: unset;
                color: #555555;
            }




            .result{
                height: 70vh;
                overflow-y: auto;
            }

            .result ul{
                width: 90%;
                margin: auto;
                padding: 0;
            }

            .result li{
                /*width: 40%;*/
                display: flex;
                margin: 30px auto;
                padding: 10px;
                list-style: none;
                border-radius: 10px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .result div{
                width: 85%;
            }

            .result form{
                width: 10%;
                margin: 0;
                margin-left: 5%;
                align-self: center;
            }
            .result button{
                padding: 10px;
                text-align: right;
                background-color: #fff;
                border: none;
            }
            .result button i{
                font-size: 16px;
            }

            .gomi {
                width: 90%;
                max-width: 400px;
                background-color: #f9f9f9;
                margin: auto;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px; /* ボタンとフォームが重ならないよう余白を確保 */
                box-sizing: border-box;
                height: 65vh;
                overflow-y: auto;
            }

            label {
                font-size: 1em;
                margin-bottom: 5px;
                display: block;
            }

            input[type="text"] {
                width: 100%;
                padding: 5px;
                margin-top: 5px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 1em;
                box-sizing: border-box;
            }

            .radio-group, .checkbox-group {
                margin-bottom: 15px;
            }

            .checkbox-group label, .radio-group label {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                cursor: pointer;
            }

            input[type="radio"], input[type="checkbox"] {
                margin-right: 10px;
                cursor: pointer;
            }

            .button-group {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap; /* スマホ対応のためボタンを折り返し可能に */
            }

            button {
                padding: 10px 20px;
                font-size: 1em;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
                /*flex: 1 1 calc(50% - 10px); !* スマホ対応：ボタンを半分の幅で表示 *!*/
                margin: 5px;
            }

            button:hover {
                transform: scale(1.05);
            }

            .btn-back {
                background-color: #FF6666;
                color: #fff;
            }

            .btn-submit {
                background-color: #66aa66;
                color: #fff;
            }
        }
    </style>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/setting.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <script>
        function toggleCheckboxes() {
            var weeklyOption = document.getElementById('weekly');
            var weeklyNthOption = document.getElementById('weeklyNth');

            if (weeklyOption.checked) {
                document.getElementById('weeklyOptions').style.display = 'block';
                document.getElementById('weeklyNthOptions').style.display = 'none';
            } else if (weeklyNthOption.checked) {
                document.getElementById('weeklyOptions').style.display = 'none';
                document.getElementById('weeklyNthOptions').style.display = 'block';
            } else {
                document.getElementById('weeklyOptions').style.display = 'none';
                document.getElementById('weeklyNthOptions').style.display = 'none';
            }

            // フォームが隠れないようスクロールを調整
            document.querySelector('form').scrollIntoView({ behavior: 'smooth' });
        }
    </script>
</head>
<body>

<div class="body">
    <div class="head">
        <button class="back" type="button" onclick="history.back()">
            <i class="fa-solid fa-circle-left"></i>
        </button>
        <h2>ゴミ出しスケジュール</h2>
    </div>
    <div class="result">
        <c:if test="${not empty scheduleList}">
            <ul>
                <c:forEach var="schedule" items="${scheduleList}">
                    <li>
                        <div>
                            <h3 style="margin: 10px 5px">${schedule.title}</h3>
                            <p style="margin: 5px">
                                <c:forEach var="week" items="${schedule.selectedWeeks}">
                                    ${week}
                                </c:forEach>
                            </p>
                            <p style="margin: 5px">
                                <c:forEach var="day" items="${schedule.selectedDays}">
                                    ${day}
                                </c:forEach>
                            </p>
                        </div>
                        <form action="DeleteScheduleServlet" method="get" onsubmit="return confirm('本当に削除しますか？');">
                            <input type="hidden" name="gomiId" value="${schedule.id}">
                            <button type="submit"><i class="fa-solid fa-trash"></i></button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${empty scheduleList}">
            <p>スケジュールがありません。</p>
        </c:if>
    </div>


    <!-- モーダルを開くボタン -->
    <div class="p">
        <div>
            <a href="#" class="open-modal-link" onclick="openModal(); return false;">
                <i class="fa-solid fa-plus"></i>
            </a>
        <%--            <p>追加</p>--%>
        </div>
    </div>
<%--    <button class="open-modal-btn" onclick="openModal()">スケジュールを登録する</button>--%>

    <!-- 登録モーダル -->
    <div id="modal" class="modal" style="display: none;" onclick="closeModalOnOutsideClick(event)">
        <div class="modal-content" onclick="event.stopPropagation()">
            <span class="close-btn" onclick="closeModal()">&times;</span>
            <h2 style="margin: 20px">ゴミ出しスケジュール登録</h2>
            <form class="gomi" id="scheduleForm" action="ScheduleServlet" method="post">
                <!-- タイトル -->
                <label for="title">出すゴミの名前を入力してください</label>
                <input type="text" id="title" name="title" required>

                <!-- ラジオボタン -->
                <div class="radio-group">
                    <label>ゴミ出しの頻度を選択してください：</label>
                    <label>
                        <input type="radio" id="weekly" name="scheduleType" value="weekly" onclick="toggleCheckboxes()" required>
                        毎週
                    </label>
                    <label>
                        <input type="radio" id="weeklyNth" name="scheduleType" value="weeklyNth" onclick="toggleCheckboxes()">
                        選択した週
                    </label>
                </div>

                <!-- 「毎週何曜日」のチェックボックス -->
                <div id="weeklyOptions" class="checkbox-group" style="display: none;">
                    <label>曜日を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weekdays" value="月曜日"> 月曜日</label>
                    <label><input type="checkbox" name="weekdays" value="火曜日"> 火曜日</label>
                    <label><input type="checkbox" name="weekdays" value="水曜日"> 水曜日</label>
                    <label><input type="checkbox" name="weekdays" value="木曜日"> 木曜日</label>
                    <label><input type="checkbox" name="weekdays" value="金曜日"> 金曜日</label>
                    <label><input type="checkbox" name="weekdays" value="土曜日"> 土曜日</label>
                    <label><input type="checkbox" name="weekdays" value="日曜日"> 日曜日</label>
                </div>

                <!-- 「第何週何曜日」のチェックボックス -->
                <div id="weeklyNthOptions" class="checkbox-group" style="display: none;">
                    <label>週を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weeks" value="第1週目"> 第1週目</label>
                    <label><input type="checkbox" name="weeks" value="第2週目"> 第2週目</label>
                    <label><input type="checkbox" name="weeks" value="第3週目"> 第3週目</label>
                    <label><input type="checkbox" name="weeks" value="第4週目"> 第4週目</label>
                    <label><input type="checkbox" name="weeks" value="第5週目"> 第5週目</label>

                    <label>曜日を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weekdays" value="月曜日"> 月曜日</label>
                    <label><input type="checkbox" name="weekdays" value="火曜日"> 火曜日</label>
                    <label><input type="checkbox" name="weekdays" value="水曜日"> 水曜日</label>
                    <label><input type="checkbox" name="weekdays" value="木曜日"> 木曜日</label>
                    <label><input type="checkbox" name="weekdays" value="金曜日"> 金曜日</label>
                    <label><input type="checkbox" name="weekdays" value="土曜日"> 土曜日</label>
                    <label><input type="checkbox" name="weekdays" value="日曜日"> 日曜日</label>
                </div>

                <!-- ボタン -->
                <div class="button-group">
                    <button type="button" class="btn-cancel" onclick="closeModal()">キャンセル</button>
                    <button type="submit" class="btn-submit">登録する</button>
                </div>
            </form>
        </div>
    </div>
    <!-- 既存のモーダルをコピーして編集モーダル用に変更 -->
    <div id="editModal" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close-btn" onclick="closeEditModal()">&times;</span>
            <h2>ゴミ出しスケジュール編集</h2>
            <form class="gomi" id="editScheduleForm" action="EditScheduleServlet" method="post">
                <!-- 既存の入力フィールドを利用 -->
                <input type="hidden" name="id" id="editId">
                <label for="editTitle">出すゴミの名前を入力してください</label>
                <input type="text" id="editTitle" name="title" required>
                <div class="radio-group">
                    <label>ゴミ出しの頻度を選択してください：</label>
                    <label>
                        <input type="radio" id="editWeekly" name="scheduleType" value="weekly" onclick="toggleEditCheckboxes()" required>
                        毎週
                    </label>
                    <label>
                        <input type="radio" id="editWeeklyNth" name="scheduleType" value="weeklyNth" onclick="toggleEditCheckboxes()">
                        選択した週
                    </label>
                </div>
                <div id="editWeeklyOptions" class="checkbox-group" style="display: none;">
                    <label>曜日を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weekdays" value="月曜日"> 月曜日</label>
                    <label><input type="checkbox" name="weekdays" value="火曜日"> 火曜日</label>
                    <label><input type="checkbox" name="weekdays" value="水曜日"> 水曜日</label>
                    <label><input type="checkbox" name="weekdays" value="木曜日"> 木曜日</label>
                    <label><input type="checkbox" name="weekdays" value="金曜日"> 金曜日</label>
                    <label><input type="checkbox" name="weekdays" value="土曜日"> 土曜日</label>
                    <label><input type="checkbox" name="weekdays" value="日曜日"> 日曜日</label>
                </div>
                <div id="editWeeklyNthOptions" class="checkbox-group" style="display: none;">
                    <label>週を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weeks" value="第1週目"> 第1週目</label>
                    <label><input type="checkbox" name="weeks" value="第2週目"> 第2週目</label>
                    <label><input type="checkbox" name="weeks" value="第3週目"> 第3週目</label>
                    <label><input type="checkbox" name="weeks" value="第4週目"> 第4週目</label>
                    <label><input type="checkbox" name="weeks" value="第5週目"> 第5週目</label>
                    <label>曜日を選択してください（複数可）：</label>
                    <label><input type="checkbox" name="weekdays" value="月曜日"> 月曜日</label>
                    <label><input type="checkbox" name="weekdays" value="火曜日"> 火曜日</label>
                    <label><input type="checkbox" name="weekdays" value="水曜日"> 水曜日</label>
                    <label><input type="checkbox" name="weekdays" value="木曜日"> 木曜日</label>
                    <label><input type="checkbox" name="weekdays" value="金曜日"> 金曜日</label>
                    <label><input type="checkbox" name="weekdays" value="土曜日"> 土曜日</label>
                    <label><input type="checkbox" name="weekdays" value="日曜日"> 日曜日</label>
                </div>
                <div class="button-group">
                    <button type="button" class="btn-cancel" onclick="closeEditModal()">キャンセル</button>
                    <button type="submit" class="btn-submit">保存する</button>
                </div>
            </form>
        </div>
    </div>

</div>

<style>
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }

    .modal-content {
        background-color: white;
        padding: 20px 10px;
        border-radius: 10px;
        width: 89%;
        max-width: 400px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .close-btn {
        position: absolute;
        top: 10px;
        right: 10px;
        font-size: 1.5em;
        cursor: pointer;
    }

    .open-modal-btn {
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
    }
</style>

<script>
    function openModal() {
        document.getElementById('modal').style.display = 'flex';
    }

    function closeModal() {
        document.getElementById('modal').style.display = 'none';
    }

    function toggleCheckboxes() {
        const isWeekly = document.getElementById('weekly').checked;
        document.getElementById('weeklyOptions').style.display = isWeekly ? 'block' : 'none';
        document.getElementById('weeklyNthOptions').style.display = !isWeekly ? 'block' : 'none';
    }

    function closeModalOnOutsideClick(event) {
        // モーダル外をクリックした場合に閉じる
        const modalContent = document.querySelector('.modal-content');
        if (!modalContent.contains(event.target)) {
            closeModal();
        }
    }

    // フォームの送信前にチェックする
    document.getElementById('scheduleForm').addEventListener('submit', function(event) {
        const weekCheckboxes = document.querySelectorAll('input[name="weeks"]:checked');
        const weekdayCheckboxes = document.querySelectorAll('input[name="weekdays"]:checked');

        if (weekCheckboxes.length === 0 && weekdayCheckboxes.length === 0) {
            alert('少なくとも1つの「週」または「曜日」を選択してください。');
            event.preventDefault(); // 送信をキャンセル
        }
    });

    // 編集モーダルを開く処理
    document.querySelectorAll('.result li').forEach(function (listItem) {
        listItem.addEventListener('click', function (e) {
            e.preventDefault();

            var id = this.querySelector('input[name="gomiId"]');
            var title = this.querySelector('h3');
            var weeks = Array.from(this.querySelectorAll('p:first-of-type span')).map(span => span.innerText.trim());
            var days = Array.from(this.querySelectorAll('p:last-of-type span')).map(span => span.innerText.trim());

            document.getElementById('editId').value = id ? id.value : '';
            document.getElementById('editTitle').value = title ? title.innerText.trim() : '';

            // Check the appropriate radio button and show relevant checkboxes
            if (weeks.length > 0) {
                document.getElementById('editWeekly').checked = true;
                document.getElementById('editWeeklyOptions').style.display = 'block';
                document.getElementById('editWeeklyNthOptions').style.display = 'none';
                // Check the days in weeks array
                weeks.forEach(week => {
                    var checkbox = document.querySelector(`#editWeeklyOptions input[value="${week}"]`);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                });
            } else if (days.length > 0) {
                document.getElementById('editWeeklyNth').checked = true;
                document.getElementById('editWeeklyOptions').style.display = 'none';
                document.getElementById('editWeeklyNthOptions').style.display = 'block';
                // Check the weeks in days array
                days.forEach(day => {
                    var checkbox = document.querySelector(`#editWeeklyNthOptions input[value="${day}"]`);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                });
            }
            openEditModal();
        });
    });

    function openEditModal() {
        document.getElementById('editModal').style.display = 'flex';
    }

    function closeEditModal() {
        document.getElementById('editModal').style.display = 'none';
    }

    function toggleEditCheckboxes() {
        const isWeekly = document.getElementById('editWeekly').checked;
        document.getElementById('editWeeklyOptions').style.display = isWeekly ? 'block' : 'none';
        document.getElementById('editWeeklyNthOptions').style.display = !isWeekly ? 'block' : 'none';
    }

    // モーダル外をクリックした場合にモーダルを閉じる処理
    window.addEventListener('click', function(event) {
        var modal = document.getElementById('editModal');
        var modalContent = document.querySelector('.modal-content');

        // モーダルが表示されている場合のみ処理を行う
        if (modal.style.display === 'flex' || modal.style.display === 'block') {
            // モーダル外部をクリックした場合
            if (event.target === modal) {
                closeEditModal(); // モーダルを閉じる
            }
        }
    });

    // モーダルのクローズボタンをクリックした場合の処理
    document.querySelector('.close-btn').addEventListener('click', function() {
        closeEditModal();
    });

    function closeEditModal() {
        document.getElementById('editModal').style.display = 'none';
    }

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