package com.example.sotukenn.kakeibo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.ExpenseDAO;
import utils.IncomeDAO;
import utils.KakeiboBean;

@WebServlet("/DeleteKakeiboServlet")
public class DeleteKakeiboServlet extends HttpServlet {
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
        String id = request.getParameter("id");
        String dataType = request.getParameter("data-type");

        System.out.println("id: " + id + ", data-type: " + dataType);

        // データベースから削除する処理
        if (dataType.equals("income")){
            IncomeDAO incomeDAO = new IncomeDAO();
            try {
                incomeDAO.deleteIncome(Integer.parseInt(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(dataType.equals("expense")){
            ExpenseDAO expenseDAO = new ExpenseDAO();
            try {
                expenseDAO.deleteExpense(Integer.parseInt(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // 元のページにリダイレクト
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        response.sendRedirect("KakeiboServlet?year=" + year + "&month=" + month + "&day=" + day);
    }

}
