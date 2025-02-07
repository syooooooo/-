package com.example.sotukenn.memo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.MemoDAO;

@WebServlet("/CreateMemoServlet")
public class CreateMemoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/memo/Create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        try {
            MemoDAO.createMemo(userId, title, content);
            response.sendRedirect("MemoListServlet");  // メモ一覧画面へリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // エラー処理
        }
    }
}

