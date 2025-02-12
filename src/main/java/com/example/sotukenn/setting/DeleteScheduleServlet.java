package com.example.sotukenn.setting;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import utils.MemoDAO;
import utils.ScheduleDAO;

@WebServlet("/DeleteScheduleServlet")
public class DeleteScheduleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int gomiId = Integer.parseInt(request.getParameter("id"));
        try {
            ScheduleDAO.deleteSchedule(gomiId);
            response.sendRedirect("ScheduleServlet");  // メモ一覧画面へリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
        