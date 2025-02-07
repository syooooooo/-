package com.example.sotukenn.login;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SignoutServlet")
public class SignoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // セッションを破棄してログアウト
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        // ログインページにリダイレクト
        response.sendRedirect("");
    }
}
        