package com.example.sotukenn.recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.RecipeBean;
import utils.RecipeDAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@WebServlet("/AddRecipeServlet")
@MultipartConfig
public class AddRecipeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/recipe/Add.jsp").forward(request, response);
        request.setCharacterEncoding("UTF-8");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        String name = request.getParameter("name");
        String description = request.getParameter("description").replace("\r\n", "\n");
        String ingredients = request.getParameter("ingredients").replace("\r\n", "\n");
        String instructions = request.getParameter("instructions").replace("\r\n", "\n");

        // レシピ情報の作成
        RecipeBean recipe = new RecipeBean(userId, name, description, ingredients, instructions);

        try {
            // レシピをデータベースに登録
            RecipeDAO.insertRecipe(recipe);
            response.sendRedirect("ListRecipeServlet"); // 登録後はレシピ一覧ページへリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}