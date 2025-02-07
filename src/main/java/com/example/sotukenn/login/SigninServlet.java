package com.example.sotukenn.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.UserBean;
import utils.UserDAO;
import utils.GenerateHash;

import java.io.IOException;

@WebServlet("/SigninServlet")
public class SigninServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // POSTパラメータ受け取り
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");  // idはString型に変更
        String password = request.getParameter("password");

        // ログイン処理
        try {
            UserBean result = UserDAO.selectById(id);  // String型のidで検索

            if (result == null) {
                response.sendRedirect("index.jsp?error=notfound");
                return;
            }

            // パスワードの検証
            if (GenerateHash.checkPassword(password, result.getPassword())) {
                // セッションにユーザー情報を保存
                HttpSession session = request.getSession();
                session.setAttribute("name", result.getName());
                session.setAttribute("user_id", result.getId());
                response.sendRedirect("CalendarServlet");
            } else {
                response.sendRedirect("index.jsp?error=invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=exception");
        }
    }
}
