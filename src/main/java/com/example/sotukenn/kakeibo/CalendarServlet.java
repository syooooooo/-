package com.example.sotukenn.kakeibo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ExpenseDAO;
import utils.ExpensetureDAO;
import utils.IncomeDAO;
import utils.ScheduleDAO;
import utils.ScheduleBean;

@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // 年と月を取得
        int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : Calendar.getInstance().get(Calendar.YEAR);
        int month = request.getParameter("month") != null ? Integer.parseInt(request.getParameter("month")) : Calendar.getInstance().get(Calendar.MONTH) + 1;

        // カレンダー生成
        List<String[]> calendarData = generateCalendar(year, month);

        // 🔹 ゴミ出しスケジュールを取得
        Map<Integer, String> garbageByDay = new HashMap<>();
        try {
            List<ScheduleBean> schedules = ScheduleDAO.getSchedulesByUserId(userId);

            for (ScheduleBean schedule : schedules) {
                String title = schedule.getTitle();
                String scheduleType = schedule.getScheduleType(); // `null` チェック済み
                String[] selectedWeeks = schedule.getSelectedWeeks();
                String[] selectedDays = schedule.getSelectedDays();

                for (String day : selectedDays) {
                    DayOfWeek dayOfWeek = ScheduleDAO.getDayOfWeek(day); // 🔹 日本語の曜日を DayOfWeek に変換

                    if ("weeklyNth".equals(scheduleType)) {
                        for (String week : selectedWeeks) {
                            int nthWeek = Integer.parseInt(week.replace("第", "").replace("週目", ""));
                            LocalDate date = ScheduleDAO.getNthWeekday(year, month, nthWeek, dayOfWeek);
                            if (date != null) {
                                garbageByDay.put(date.getDayOfMonth(), title.length() > 3 ? title.substring(0, 3) : title);
                            }
                        }
                    } else if ("weekly".equals(scheduleType)) {
                        List<LocalDate> dates = ScheduleDAO.getWeeklyDays(year, month, dayOfWeek);
                        for (LocalDate date : dates) {
                            garbageByDay.put(date.getDayOfMonth(), title.length() > 3 ? title.substring(0, 3) : title);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("ゴミ出しスケジュールの取得エラー", e);
        }

        // 🔹 ゴミ出しデータをリクエストスコープにセット
        request.setAttribute("garbageByDay", garbageByDay);

        // 収入・支出データ（元の処理は変更せず維持）
        Map<Integer, Integer> incomeByDay = new HashMap<>();
        Map<Integer, Integer> expenseByDay = new HashMap<>();
        try {
            incomeByDay = IncomeDAO.getTotalIncomeByMonthGroupedByDay(userId, year, month);
            Map<Integer, Integer> expense = ExpenseDAO.getTotalExpenseByMonthGroupedByDay(userId, year, month);
            Map<Integer, Integer> expenses = ExpensetureDAO.getTotalExpensesGroupedByDay(userId);

            // 現在の年月
            YearMonth currentYearMonth = YearMonth.of(year, month);
            int lastDayOfMonth = currentYearMonth.lengthOfMonth(); // その月の最終日

            expenseByDay = new HashMap<>(expense);
//            for (Map.Entry<Integer, Integer> entry : expenses.entrySet()) {
//                expenseByDay.merge(entry.getKey(), entry.getValue(), Integer::sum);
//            }
            for (Map.Entry<Integer, Integer> entry : expenses.entrySet()) {
                int day = entry.getKey();
                int totalExpense = entry.getValue();

                // 不正な日付なら月末に修正
                if (day > lastDayOfMonth) {
                    day = lastDayOfMonth;
                }

                // データをマージ（既に存在する場合は加算）
                expenseByDay.merge(day, totalExpense, Integer::sum);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> incomeByGenre = new HashMap<>();
        try {
            incomeByGenre = IncomeDAO.getIncomeByGenre(userId, year, month);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Integer income1 = incomeByGenre.get("給料");
        if (income1 == null) {
            income1 = 0; // デフォルト値として0を設定
        }
        Integer income2 = incomeByGenre.get("副業");
        if (income2 == null) {
            income2 = 0; // デフォルト値として0を設定
        }
        Integer income3 = incomeByGenre.get("投資");
        if (income3 == null) {
            income3 = 0; // デフォルト値として0を設定
        }
        Integer income4 = incomeByGenre.get("臨時収入");
        if (income4 == null) {
            income4 = 0; // デフォルト値として0を設定
        }
        Integer income5 = incomeByGenre.get("その他");
        if (income5 == null) {
            income5 = 0; // デフォルト値として0を設定
        }
        String jsonIncomeByGenre = new Gson().toJson(incomeByGenre);
        request.setAttribute("incomeByGenre", jsonIncomeByGenre);

        Map<String, Integer> expenseByGenre = new HashMap<>();
        try {
//            expenseByGenre = ExpenseDAO.getExpenseByGenre(userId, year, month);
            Map<String, Integer> expense = ExpenseDAO.getExpenseByGenre(userId, year, month);
            Map<String, Integer> expenses = ExpensetureDAO.getExpensesByGenre(userId);

            expenseByGenre = new HashMap<>(expense); // 最初にexpenseで初期化

            // expensesのデータをexpenseByDayに統合
            for (Map.Entry<String, Integer> entry : expenses.entrySet()) {
                expenseByGenre.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Integer expense1 = expenseByGenre.get("生活費");
        if (expense1 == null) {
            expense1 = 0; // デフォルト値として0を設定
        }
        Integer expense2 = expenseByGenre.get("娯楽費");
        if (expense2 == null) {
            expense2 = 0; // デフォルト値として0を設定
        }
        Integer expense3 = expenseByGenre.get("交通費");
        if (expense3 == null) {
            expense3 = 0; // デフォルト値として0を設定
        }
        Integer expense4 = expenseByGenre.get("医療費");
        if (expense4 == null) {
            expense4 = 0; // デフォルト値として0を設定
        }
        Integer expense5 = expenseByGenre.get("その他");
        if (expense5 == null) {
            expense5 = 0; // デフォルト値として0を設定
        }
        String jsonExpenseByGenre = new Gson().toJson(expenseByGenre);
        System.out.println("jsonExpenseByGenre: " + jsonExpenseByGenre);
        request.setAttribute("expenseByGenre", jsonExpenseByGenre);

        request.setAttribute("income1", income1);
        request.setAttribute("income2", income2);
        request.setAttribute("income3", income3);
        request.setAttribute("income4", income4);
        request.setAttribute("income5", income5);
        request.setAttribute("expense1", expense1);
        request.setAttribute("expense2", expense2);
        request.setAttribute("expense3", expense3);
        request.setAttribute("expense4", expense4);
        request.setAttribute("expense5", expense5);

        request.setAttribute("incomeByDay", incomeByDay);
        request.setAttribute("expenseByDay", expenseByDay);
        request.setAttribute("calendarData", calendarData);
        request.setAttribute("year", year);
        request.setAttribute("month", month);

        // 月の収入と支出のデータを取得
        try {
            int totalIncomeByMonth = IncomeDAO.getTotalIncomeByMonth(userId, year, month);
            request.setAttribute("totalIncomeByMonth", totalIncomeByMonth);
//            int totalExpenseByMonth = ExpenseDAO.getTotalExpenseByMonth(userId, year, month);
            int expense = ExpenseDAO.getTotalExpenseByMonth(userId, year, month);
            int expenses = ExpensetureDAO.getTotalExpenses(userId);
            int totalExpenseByMonth = expense + expenses;
            request.setAttribute("totalExpenseByMonth", totalExpenseByMonth);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 🔹 JSP にフォワード
        if (session.getAttribute("name") != null) {
            request.getRequestDispatcher("WEB-INF/view/kakeibo/Calendar.jsp").forward(request, response);
        } else {
            response.sendRedirect("");
        }
    }

    private List<String[]> generateCalendar(int year, int month) {
        List<String[]> weeks = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[] week = new String[7];
        int dayCounter = 1;

        for (int i = 0; i < firstDayOfWeek - 1; i++) {
            week[i] = "";
        }

        for (int i = firstDayOfWeek - 1; i < 7; i++) {
            week[i] = String.valueOf(dayCounter++);
        }
        weeks.add(week);

        while (dayCounter <= daysInMonth) {
            week = new String[7];
            for (int i = 0; i < 7; i++) {
                if (dayCounter <= daysInMonth) {
                    week[i] = String.valueOf(dayCounter++);
                } else {
                    week[i] = "";
                }
            }
            weeks.add(week);
        }

        return weeks;
    }
}
