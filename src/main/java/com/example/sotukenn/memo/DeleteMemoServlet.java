package com.example.sotukenn.memo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.MemoDAO;

@WebServlet("/DeleteMemoServlet")
public class DeleteMemoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int memoId = Integer.parseInt(request.getParameter("memoId"));

        try {
            MemoDAO.deleteMemo(memoId);
            response.sendRedirect("MemoListServlet");  // メモ一覧画面へリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

