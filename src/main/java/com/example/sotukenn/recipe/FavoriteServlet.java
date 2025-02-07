package com.example.sotukenn.recipe;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.FavoriteBean;
import utils.FavoriteDAO;

@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        // セッションからユーザーIDを取得
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // お気に入りのレシピを取得
        List<FavoriteBean> favoriteRecipes = FavoriteDAO.getFavoritesByUserId(userId);

        // JSP にお気に入りレシピを渡す
        request.setAttribute("recipes", favoriteRecipes);
        request.getRequestDispatcher("WEB-INF/view/recipe/Favorite.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // セッションから user_id を取得
        String userId = (String) session.getAttribute("user_id");

        // リクエストから recipeId を取得
        String recipeIdParam = request.getParameter("recipeId");

        // recipeId が null または空ならエラーメッセージを返す
        if (recipeIdParam == null || recipeIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("{\"error\":\"recipe_id is required\"}");
            return;
        }

        int recipeId;
        try {
            recipeId = Integer.parseInt(recipeIdParam); // recipeIdを整数に変換
        } catch (NumberFormatException e) {
            // recipe_id が整数として不正な場合のエラーハンドリング
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            return;
        }

        if (FavoriteDAO.isFavorite(userId, recipeId)) {
            FavoriteDAO.removeFavorite(userId, recipeId);
        } else {
            FavoriteDAO.addFavorite(userId, recipeId);
        }

        // 元の画面にリダイレクト
        String referer = request.getHeader("Referer"); // 元のリクエスト元を取得
        if (referer != null) {
            response.sendRedirect(referer); // 元の画面に戻る
        } else {
            response.sendRedirect("WEB-INF/view/recipe/Search.jsp"); // Referer が null の場合のデフォルトページ
        }
    }
}
