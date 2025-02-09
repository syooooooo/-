<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>メモ一覧</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/memo.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body class="list">
<div class="body">
    <div class="h1">
        <h1>メモ一覧</h1>
    </div>

    <div class="results">
        <c:choose>
            <c:when test="${not empty memoList}">
                <c:forEach var="memo" items="${memoList}">
                    <div class="result">
                        <a href="EditMemoServlet?memoId=${memo.id}" class="title">
                            <h3>${memo.title}</h3>
                            <form action="DeleteMemoServlet" method="get" onsubmit="return confirm('本当に削除しますか？');">
                                <input type="hidden" name="memoId" value="${memo.id}">
                                <button type="submit"><i class="fa-solid fa-trash"></i></button>
                            </form>
                        </a>
                        <p>更新日: <fmt:formatDate value="${memo.update_day}" pattern="yyyy-MM-dd HH:mm" /></p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>メモはありません。</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="p">
        <div>
            <a href="CreateMemoServlet"><i class="fa-solid fa-plus"></i></a>
            <%--            <p>追加</p>--%>
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
</div>

</body>
</html>
