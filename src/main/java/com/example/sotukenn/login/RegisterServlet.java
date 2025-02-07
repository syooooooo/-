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
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 登録画面を表示
        request.getRequestDispatcher("WEB-INF/view/login/Register.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8"); // レスポンスのエンコーディング設定
        response.setCharacterEncoding("UTF-8");

        // action パラメータを取得
        String action = request.getParameter("action");

        // action が null でない場合、フォームが送信されたことを意味する
        if (action == null) {
            // 登録用のパラメータを取得
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword"); // 確認用パスワード

            // IDがすでに登録済みかどうかチェック
            try {
                if (UserDAO.isIdRegistered(id)) { // ここでIDの存在をチェック
                    // 登録済みの場合、エラーページにリダイレクト
                    response.sendRedirect("error.jsp?error=idExists");
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // パスワード確認のチェック
            if (!password.equals(confirmPassword)) {
                // パスワードが一致しない場合、エラーページにリダイレクト
                response.sendRedirect("error.jsp");
                return;
            }

            // ソルトを生成
            String salt = GenerateHash.getSalt();
            // パスワードをハッシュ化
            String hashedPassword = GenerateHash.getHashPw(password, salt);

            // ユーザーオブジェクト作成（saltも渡す）
            UserBean user = new UserBean(id, name, hashedPassword, salt);

            // セッションに保存して確認ページに遷移
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // ユーザーオブジェクトをセッションに保存

            // 確認画面へ
            try {
                request.getRequestDispatcher("/WEB-INF/view/login/ConfirmRegister.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else if ("register".equals(action)) {
            // 確認ページから登録する場合
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("user");

            if (user != null) {
                // DBに登録処理
//                try {
                UserDAO.insert(user); // ユーザー登録
                session.invalidate(); // セッション無効化
                response.sendRedirect("index.jsp"); // ログインページにリダイレクト
//                } catch (Exception e) {
//                    // エラーページにリダイレクト
//                    response.sendRedirect("error.jsp");
//                }
            }
        }
    }
}
