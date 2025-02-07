package com.example.sotukenn.setting;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import utils.ScheduleDAO;

@WebServlet("/EditScheduleServlet")
public class EditScheduleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("user_id");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String scheduleType = request.getParameter("scheduleType");
        String[] selectedDays = request.getParameterValues("weekdays");
        String[] selectedWeeks = request.getParameterValues("weeks");
        if (selectedWeeks == null) {
            selectedWeeks = new String[] {"毎週"};  // 空の配列を設定
        }

        try {
            ScheduleDAO.updateSchedule(id, title, scheduleType, selectedWeeks, selectedDays);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("ScheduleServlet");
    }
}
        