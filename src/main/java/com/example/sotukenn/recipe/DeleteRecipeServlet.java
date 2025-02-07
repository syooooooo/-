package com.example.sotukenn.recipe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.RecipeBean;
import utils.RecipeDAO;

@WebServlet("/DeleteRecipeServlet")
public class DeleteRecipeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        request.setAttribute("user_id", userId);

        try {
            List<RecipeBean> recipes = RecipeDAO.getAllRecipes(userId);
            request.setAttribute("recipes", recipes);
            System.out.println("Number of recipes: " + recipes.size());
            request.getRequestDispatcher("WEB-INF/view/recipe/Delete.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        try {
            RecipeDAO.deleteRecipes(recipeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String referer = request.getHeader("Referer"); // 元のリクエスト元を取得
        if (referer != null) {
            response.sendRedirect(referer); // 元の画面に戻る
        } else {
            response.sendRedirect("WEB-INF/view/recipe/Search.jsp"); // Referer が null の場合のデフォルトページ
        }
    }
}
        