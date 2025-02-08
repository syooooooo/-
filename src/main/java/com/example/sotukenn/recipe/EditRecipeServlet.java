package com.example.sotukenn.recipe;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.MemoBean;
import utils.MemoDAO;
import utils.RecipeBean;
import utils.RecipeDAO;

@WebServlet("/EditRecipeServlet")
public class EditRecipeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String recipeId = (String) session.getAttribute("recipeId");
        RecipeBean recipe = null;
        try {
            // メモIDに対応する1件のメモを取得
            recipe = RecipeDAO.getRecipeById(Integer.parseInt(recipeId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        System.out.println("Received recipe name: " + recipe.getName());
//        System.out.println("Received recipe description: " + recipe.getDescription());
//        System.out.println("Received recipe ingredients: " + recipe.getIngredients());
//        System.out.println("Received recipe instructions: " + recipe.getInstructions());


        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("WEB-INF/view/recipe/Edit.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

//        // セッションから recipeId を取得
//        String recipeIdStr = (String) session.getAttribute("id");
//
//        // recipeId のバリデーション
//        if (recipeIdStr == null || recipeIdStr.isEmpty()) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "recipeId is missing in session");
//            return;
//        }
//
//        int recipeId;
//        try {
//            recipeId = Integer.parseInt(recipeIdStr); // String -> int に変換
//        } catch (NumberFormatException e) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recipeId format");
//            return;
//        }

        int recipeId = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("name");
        System.out.println("Received recipe name: " + name);

        String description = request.getParameter("description").replace("\r\n", "\n");
        System.out.println("Received recipe description: " + description);

        String ingredients = request.getParameter("ingredients").replace("\r\n", "\n");
        System.out.println("Received recipe ingredients: " + ingredients);

        String instructions = request.getParameter("instructions").replace("\r\n", "\n");
        System.out.println("Received recipe instructions: " + instructions);

        RecipeDAO.editRecipes(recipeId, name, description, ingredients, instructions);

        HttpSession session = request.getSession();
        session.setAttribute("recipeId", String.valueOf(recipeId));
//        response.sendRedirect("RecipeDetailServlet");
        response.sendRedirect("RecipeDetailServlet?recipeId=" + recipeId);
    }
}
