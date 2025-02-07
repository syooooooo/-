package com.example.sotukenn.memo;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.MemoBean;
import utils.MemoDAO;

@WebServlet("/MemoListServlet")
public class MemoListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
//        if (session.getAttribute("name") != null){
//            request.getRequestDispatcher("WEB-INF/view/memo/List.jsp").forward(request, response);
//        }else{
//            response.sendRedirect("");
//        }

        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            // セッションにユーザーIDが存在しない場合
            System.err.println("Error: No user_id found in session.");
            response.sendRedirect(""); // ログイン画面へリダイレクト
            return;
        }

        try {
            List<MemoBean> memoList = MemoDAO.getMemosByUserId(userId);
            request.setAttribute("memoList", memoList);
            request.getRequestDispatcher("WEB-INF/view/memo/List.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching memos for user_id: " + userId);
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
