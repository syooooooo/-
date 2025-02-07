package com.example.sotukenn.memo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.MemoBean;
import utils.MemoDAO;

@WebServlet("/EditMemoServlet")
public class EditMemoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memoId = request.getParameter("memoId");
        MemoBean memo = null;
        try {
            // メモIDに対応する1件のメモを取得
            memo = MemoDAO.getMemoById(Integer.parseInt(memoId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // メモをJSPに渡す
        request.setAttribute("memo", memo);
        request.getRequestDispatcher("WEB-INF/view/memo/Edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int memoId = Integer.parseInt(request.getParameter("memoId"));
        String title = request.getParameter("title");
        String content = request.getParameter("text");

        try {
            // メモの更新
            MemoDAO.updateMemo(memoId, title, content);
            response.sendRedirect("MemoListServlet");  // メモ一覧画面へリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
