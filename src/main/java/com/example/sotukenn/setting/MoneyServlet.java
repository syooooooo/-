package com.example.sotukenn.setting;

import jakarta.servlet.http.HttpSession;
import utils.MoneyDAO;
import utils.MoneyBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/MoneyServlet")
public class MoneyServlet extends HttpServlet {
    private MoneyDAO moneyDAO = new MoneyDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        request.setAttribute("user_id", userId);

        MoneyBean moneyBean = moneyDAO.getMoneyBean(userId); // MoneyBean を取得
        request.setAttribute("goalAmount", moneyBean.getGoalAmount());
        request.getRequestDispatcher("WEB-INF/view/setting/Money.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        request.setAttribute("user_id", userId);

        try {
            // フォームから目標金額を取得
            int goalAmount = Integer.parseInt(request.getParameter("goalAmount"));
            if(moneyDAO.isGoalAmountExists(userId)){
                moneyDAO.updateGoalAmount(userId,goalAmount); // 更新処理
                request.setAttribute("message", "目標金額を更新しました！");
            }else {
                moneyDAO.createGoalAmount(userId,goalAmount);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("message", "正しい金額を入力してください。");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        doGet(request, response); // 更新後のデータを再表示
        response.sendRedirect("MoneyServlet");
    }
}