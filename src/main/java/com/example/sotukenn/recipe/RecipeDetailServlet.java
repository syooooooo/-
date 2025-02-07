package com.example.sotukenn.recipe;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.RecipeBean;
import utils.RecipeDAO;
import utils.FavoriteDAO;

@WebServlet("/RecipeDetailServlet")
public class RecipeDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        // リクエストからレシピIDを取得
        String recipeIdParam = request.getParameter("recipeId");
        if (recipeIdParam == null || recipeIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("{\"error\":\"recipe_id is required\"}");
            return;
        }

        int recipeId;
        try {
            recipeId = Integer.parseInt(recipeIdParam); // recipeIdを整数に変換
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            return;
        }

        // レシピの詳細情報を取得
        RecipeBean recipe = null;
        try {
            recipe = RecipeDAO.getRecipeById(recipeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (recipe == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
            response.getWriter().write("{\"error\":\"recipe not found\"}");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("recipeId", String.valueOf(recipeId));

        // 改行を <br> に変換して JSP に渡す
        request.setAttribute("descriptionHtml", recipe.getDescription().replace("\n", "<br>"));
        request.setAttribute("ingredientsHtml", recipe.getIngredients().replace("\n", "<br>"));
        request.setAttribute("instructionsHtml", recipe.getInstructions().replace("\n", "<br>"));

        // `textarea` 用に改行そのままのデータも渡す
        request.setAttribute("description", recipe.getDescription());
        request.setAttribute("ingredients", recipe.getIngredients());
        request.setAttribute("instructions", recipe.getInstructions());

        // JSPにレシピの詳細情報を渡す
        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("WEB-INF/view/recipe/Detail.jsp").forward(request, response);
    }
}
