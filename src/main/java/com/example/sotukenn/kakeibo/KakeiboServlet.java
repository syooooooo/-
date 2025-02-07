package com.example.sotukenn.kakeibo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDate;
import java.sql.Date;

@WebServlet("/KakeiboServlet")
public class KakeiboServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String userId = (String) session.getAttribute("user_id");
            request.setAttribute("user_id", userId);

            int year = Integer.parseInt(request.getParameter("year"));
            int month = Integer.parseInt(request.getParameter("month"));
            int day = Integer.parseInt(request.getParameter("day"));

            // 日付計算
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);

            // 前日
            calendar.add(Calendar.DATE, -1);
            String previousDay = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

            // 翌日
            calendar.add(Calendar.DATE, 2);
            String nextDay = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

            // 必要なデータをJSPに渡す
            request.setAttribute("previousDay", previousDay);
            request.setAttribute("nextDay", nextDay);
            request.setAttribute("year", year);
            request.setAttribute("month", month);
            request.setAttribute("day", day);

            // 収入リストと支出リストを取得
            IncomeDAO incomeDAO = new IncomeDAO();
            ExpenseDAO expenseDAO = new ExpenseDAO();
            ExpensetureDAO expensetureDAO = new ExpensetureDAO();
            List<KakeiboBean> incomeList = incomeDAO.getIncomeByDate(userId, year, month, day);

            List<KakeiboBean> expense = expenseDAO.getExpenseByDate(userId, year, month, day);
            List<ExpensetureBean> expenses = expensetureDAO.getExpensesByDate(userId, day);

            // expenses リストの日付を計算して設定する処理
            List<ExpensetureBean> expensesList = new ArrayList<>();
            for (ExpensetureBean expenseBean : expenses) {
                // year と month を使って日付を計算
                int date = year * 10000 + month * 100 + expenseBean.getDay();  // YYYYMMDD

                // 月と日が1桁の場合にゼロ埋め
                String formattedMonth = String.format("%02d", month);
                String formattedDay = String.format("%02d", expenseBean.getDay());

                // yyyy-MM-dd の形式にフォーマット
                String dateString = year + "-" + formattedMonth + "-" + formattedDay;

                // LocalDateに変換するために
                LocalDate localDate = LocalDate.of(year, month, expenseBean.getDay());

// 結果を bean に設定
                expenseBean.setDate(localDate);

                expensesList.add(expenseBean);  // expensesList に追加
            }

            // 支出リストを正しく設定
            List<Object> expenseList = new ArrayList<>();
            expenseList.addAll(expense);  // 支出データのみを追加
            expenseList.addAll(expensesList);  // 日付がセットされた支出データを追加

            // JSPにデータを渡す
            request.setAttribute("incomeList", incomeList);  // 収入データ
            request.setAttribute("expenseList", expenseList);  // 支出データ

            // JSPに転送
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/kakeibo/Kakeibo.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("user_id"); // セッションから user_id を取得

        // フォームから送信されたデータを取得
        String type = request.getParameter("type");
        String genre = request.getParameter("genre");
        String title = request.getParameter("title");
        String amountStr = request.getParameter("amount");
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");
        String dayStr = request.getParameter("day");

        // 日付を指定するためのデータ
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);

        // 日付を java.sql.Date に変換
        java.sql.Date date = new java.sql.Date(new java.util.GregorianCalendar(year, month - 1, day).getTimeInMillis());

        try {
            int amount = Integer.parseInt(amountStr);

            // `type` による分岐処理
            if ("income".equals(type)) {
                IncomeDAO incomeDAO = new IncomeDAO();
                KakeiboBean income = new KakeiboBean(user_id, date, genre, title, amount);
                incomeDAO.saveIncome(income); // 収入を保存
            } else if ("expense".equals(type)) {
                ExpenseDAO expenseDAO = new ExpenseDAO();
                KakeiboBean expense = new KakeiboBean(user_id, date, genre, title, amount);
                expenseDAO.saveExpense(expense); // 支出を保存
            } else {
                // 無効な type の場合
                throw new IllegalArgumentException("Invalid type: " + type);
            }

            // データ登録後、前のページにリダイレクト
            response.sendRedirect("KakeiboServlet?year=" + request.getParameter("year") +
                    "&month=" + request.getParameter("month") +
                    "&day=" + request.getParameter("day"));
        } catch (NumberFormatException e) {
            // エラーハンドリング
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for amount.");
        } catch (IllegalArgumentException e) {
            // 無効な type の場合のエラー
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            // DBエラーハンドリング
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }

}
