package com.example.sotukenn.recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.RecipeBean;
import utils.RecipeDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/ListRecipeServlet")
public class ListRecipeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        request.setAttribute("user_id", userId);

        try {
            List<RecipeBean> recipes = RecipeDAO.getAllRecipes(userId);
            request.setAttribute("recipes", recipes);
            System.out.println("Number of recipes: " + recipes.size());
            request.getRequestDispatcher("WEB-INF/view/recipe/List.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}