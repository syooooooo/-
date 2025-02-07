//package com.example.sotukenn.setting;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import utils.ExpensetureBean;
//import utils.ExpensetureDAO;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@WebServlet("/ExpensetureServlet")
//public class ExpensetureServlet extends HttpServlet {
//
//    private ExpensetureDAO dao = new ExpensetureDAO(); // DAOのインスタンス
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8");
//        request.setCharacterEncoding("UTF-8");
//
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("user_id");
//
//        // DAOから全件データを取得し、ジャンルごとにグループ化
//        List<ExpensetureBean> expenses = dao.getAllExpenses(userId);
//
//        Map<String, List<ExpensetureBean>> groupedExpenses = expenses.stream()
//                .collect(Collectors.groupingBy(ExpensetureBean::getGenre));
//
//        // JSPにデータを渡す
//        request.setAttribute("groupedExpenses", groupedExpenses);
//
//        // JSPにフォワード
//        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/setting/Expenseture.jsp");
//        dispatcher.forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8");
//        request.setCharacterEncoding("UTF-8");
//
//        String idString = request.getParameter("id");
//        System.out.println("Received id: " + idString);
//
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("user_id");
//
//        String action = request.getParameter("action");
//
//        if ("delete".equals(action)) {
//            // 削除処理
//            int id = Integer.parseInt(request.getParameter("id"));
//            dao.deleteExpenseById(id);
//        } else {
//            // 追加処理
//            String genre = request.getParameter("genre");
//            String title = request.getParameter("title");
//
//            String dateStr = request.getParameter("date");
//
//            if (dateStr == null || dateStr.isEmpty()) {
//                response.getWriter().println("日付が入力されていません。");
//                return;
//            }
//
//            LocalDate date = null;
//
//            try {
//                date = LocalDate.parse(dateStr);  // "yyyy-MM-dd" の形式で変換
//            } catch (DateTimeParseException e) {
//                response.getWriter().println("日付の形式が正しくありません。");
//            }
//
//            int amount = Integer.parseInt(request.getParameter("amount"));
//
//            ExpensetureBean expense = new ExpensetureBean(userId, genre, title, amount, date);
//            dao.addExpense(expense);
//        }
//
//        // GETリクエストにリダイレクトして画面を再描画
//        response.sendRedirect("ExpensetureServlet");
//    }
//}
package com.example.sotukenn.setting;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ExpensetureBean;
import utils.ExpensetureDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.time.LocalDate;
import java.sql.Date;

@WebServlet("/ExpensetureServlet")
public class ExpensetureServlet extends HttpServlet {

    private ExpensetureDAO dao = new ExpensetureDAO(); // DAOのインスタンス

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // DAOから全件データを取得
        List<ExpensetureBean> expenses = dao.getAllExpenses(userId);

        // 日付（日にち部分）でグループ化
        Map<Integer, List<ExpensetureBean>> groupedByDay = expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getDay()));  // 日にち部分だけを取得

        // 日にち順にソート
        Map<Integer, List<ExpensetureBean>> sortedGroupedByDay = new TreeMap<>(groupedByDay);  // TreeMapで自動的に日にち順にソート

        // JSPにデータを渡す
        request.setAttribute("groupedByDay", sortedGroupedByDay);

        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/setting/Expenseture.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String idString = request.getParameter("id");
        System.out.println("Received id: " + idString);

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // 削除処理
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteExpenseById(id);
        } else {
            // 追加処理
            String genre = request.getParameter("genre");
            String title = request.getParameter("title");
            int date = Integer.parseInt(request.getParameter("date"));

//            String dateStr = request.getParameter("date");
//
//            if (dateStr == null || dateStr.isEmpty()) {
//                response.getWriter().println("日付が入力されていません。");
//                return;
//            }
//
//            LocalDate date = null;
//
//            try {
//                date = LocalDate.parse(dateStr);  // "yyyy-MM-dd" の形式で変換
//            } catch (DateTimeParseException e) {
//                response.getWriter().println("日付の形式が正しくありません。");
//                return;
//            }

            int amount = Integer.parseInt(request.getParameter("amount"));

            ExpensetureBean expense = new ExpensetureBean(userId, genre, title, amount, date);
            dao.addExpense(expense);
        }

        // GETリクエストにリダイレクトして画面を再描画
        response.sendRedirect("ExpensetureServlet");
    }
}
