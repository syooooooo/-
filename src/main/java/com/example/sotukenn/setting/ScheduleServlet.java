package com.example.sotukenn.setting;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import utils.ScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import utils.ScheduleBean;

import java.io.IOException;
import java.util.List;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        try {
            List<ScheduleBean> scheduleList = ScheduleDAO.getSchedulesByUserId(userId);
            request.setAttribute("scheduleList", scheduleList);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching memos for user_id: " + userId);
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/setting/Schedule.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect("login.jsp"); // ログインページへリダイレクト
            return;
        }

        // フォームからデータを取得
        String title = request.getParameter("title");
        String scheduleType = request.getParameter("scheduleType");
        String[] selectedDays = request.getParameterValues("weekdays");
        String[] selectedWeeks = request.getParameterValues("weeks");
        if (selectedWeeks == null) {
            selectedWeeks = new String[] {"毎週"};  // 空の配列を設定
        }

        // ScheduleBean にデータをセット
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setUser_id(userId);
        scheduleBean.setTitle(title);
        scheduleBean.setScheduleType(scheduleType);
        scheduleBean.setSelectedDays(selectedDays);
        scheduleBean.setSelectedWeeks(selectedWeeks);

        // DAO を使ってデータを保存
        ScheduleDAO dao = new ScheduleDAO();
        dao.saveSchedule(scheduleBean);

        // 保存完了後、結果ページにリダイレクト
        request.setAttribute("schedule", scheduleBean);
//        request.getRequestDispatcher("/WEB-INF/view/setting/Schedule.jsp").forward(request, response);
        response.sendRedirect("ScheduleServlet");
    }
}