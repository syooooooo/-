package com.example.sotukenn.kakeibo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.ExpenseDAO;
import utils.IncomeDAO;
import utils.KakeiboBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@WebServlet("/EditKakeiboServlet")
public class EditKakeiboServlet extends HttpServlet {
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
            List<KakeiboBean> incomeList = incomeDAO.getIncomeByDate(userId, year, month, day);
            List<KakeiboBean> expenseList = expenseDAO.getExpenseByDate(userId, year, month, day);

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
        String user_id = (String) session.getAttribute("user_id");

        // フォームから送信されたデータを取得
        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("editType");
        String genre = request.getParameter("genre");
        String title = request.getParameter("title");
        String amountStr = request.getParameter("amount");
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");
        String dayStr = request.getParameter("day");

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);
        int day = Integer.parseInt(dayStr);
        java.sql.Date date = new java.sql.Date(new java.util.GregorianCalendar(year, month - 1, day).getTimeInMillis());

        try {
            int amount = Integer.parseInt(amountStr);

            if ("income".equals(type)) {
                IncomeDAO incomeDAO = new IncomeDAO();
                KakeiboBean income = new KakeiboBean(genre, title, amount, id);
                incomeDAO.updateIncome(income);
            } else if ("expense".equals(type)) {
                ExpenseDAO expenseDAO = new ExpenseDAO();
                KakeiboBean expense = new KakeiboBean(genre, title, amount, id);
                expenseDAO.updateExpense(expense);
            } else {
                throw new IllegalArgumentException("Invalid type: " + type);
            }

            response.sendRedirect("KakeiboServlet?year=" + year + "&month=" + month + "&day=" + day);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for amount.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }
}
