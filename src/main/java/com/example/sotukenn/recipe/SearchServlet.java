package com.example.sotukenn.recipe;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("name") != null){
            request.getRequestDispatcher("WEB-INF/view/recipe/Search.jsp").forward(request, response);
        }else{
            response.sendRedirect("");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        request.setAttribute("user_id", userId);

        // 検索キーワードを取得
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = ""; // 空のキーワードの場合、すべてのレシピを表示
        }

        request.setAttribute("keyword", keyword);

        // データベースから検索結果を取得
        List<RecipeBean> recipes = RecipeDAO.searchRecipes(userId, keyword) ;

        // 結果をリクエスト属性にセット
        request.setAttribute("recipes", recipes);

        // 検索結果ページにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/recipe/SearchResult.jsp");
        dispatcher.forward(request, response);
    }
}
        