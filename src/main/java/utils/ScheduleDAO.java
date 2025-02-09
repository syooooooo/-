package utils;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleDAO {
    private static final String DB_URL = "jdbc:mysql://163.44.96.125:3306/sotukenn?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "O-syougo0317";

    // 📌 スケジュールを保存する
    public void saveSchedule(ScheduleBean schedule) {
        String sql = "INSERT INTO schedules (user_id, title, schedule_type, weeks, weekdays) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, schedule.getUser_id());
            pstmt.setString(2, schedule.getTitle());
            pstmt.setString(3, schedule.getScheduleType());
            pstmt.setString(4, schedule.getSelectedWeeks() != null ? String.join(",", schedule.getSelectedWeeks()) : null);
            pstmt.setString(5, schedule.getSelectedDays() != null ? String.join(",", schedule.getSelectedDays()) : null);

            pstmt.executeUpdate();
            System.out.println("スケジュールがデータベースに保存されました。");

        } catch (SQLException e) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, "データベースエラー", e);
        }
    }

    // 📌 ユーザーIDでスケジュールを取得
    public static List<ScheduleBean> getSchedulesByUserId(String userId) throws SQLException {
        String sql = "SELECT id, title, schedule_type, weeks, weekdays FROM schedules WHERE user_id = ?";
        List<ScheduleBean> scheduleList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ScheduleBean schedule = new ScheduleBean();
                schedule.setId(rs.getInt("id"));
                schedule.setTitle(rs.getString("title"));

                String weeks = rs.getString("weeks");
                String weekdays = rs.getString("weekdays");

                schedule.setSelectedWeeks(weeks != null ? weeks.split(",") : new String[0]);
                schedule.setSelectedDays(weekdays != null ? weekdays.split(",") : new String[0]);

                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            Logger.getLogger(ScheduleDAO.class.getName()).log(Level.SEVERE, "Error fetching schedules", e);
            throw new SQLException("Error fetching schedules", e);
        }

        return scheduleList;
    }

    // 📌 スケジュールを更新
    public static void updateSchedule(int id, String title, String scheduleType, String[] weeks, String[] weekDays) throws SQLException {
        String sql = "UPDATE schedules SET title = ?, schedule_type = ?, weeks = ?, weekdays = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, scheduleType);
            pstmt.setString(3, String.join(",", weeks));
            pstmt.setString(4, String.join(",", weekDays));
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error while updating schedule", e);
        }
    }

    // 📌 スケジュールを削除
    public static void deleteSchedule(int scheduleId) throws SQLException {
        String query = "DELETE FROM schedules WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            stmt.executeUpdate();
        }
    }

    // 📌 第n週の曜日を取得
    public static LocalDate getNthWeekday(int year, int month, int nth, DayOfWeek dayOfWeek) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDay = yearMonth.atDay(1);
        int firstWeekdayOffset = (dayOfWeek.getValue() - firstDay.getDayOfWeek().getValue() + 7) % 7;
        LocalDate targetDate = firstDay.plusDays(firstWeekdayOffset + (nth - 1) * 7);
        return targetDate.getMonthValue() == month ? targetDate : null;
    }

    // 📌 毎週の曜日を取得
    public static List<LocalDate> getWeeklyDays(int year, int month, DayOfWeek dayOfWeek) {
        List<LocalDate> dates = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate date = yearMonth.atDay(1);

        while (date.getMonthValue() == month) {
            if (date.getDayOfWeek() == dayOfWeek) {
                dates.add(date);
            }
            date = date.plusDays(1);
        }

        return dates;
    }

    // 📌 ゴミ出しスケジュールの日付リストを取得
    public static List<LocalDate> getGarbageDates(int year, int month, String userId) throws SQLException {
        List<LocalDate> garbageDates = new ArrayList<>();
        List<ScheduleBean> schedules = getSchedulesByUserId(userId);

        for (ScheduleBean schedule : schedules) {
            if (schedule.getScheduleType().equals("weeklyNth")) {
                for (String week : schedule.getSelectedWeeks()) {
                    int nthWeek = Integer.parseInt(week.replace("第", "").replace("週目", ""));
                    for (String day : schedule.getSelectedDays()) {
                        DayOfWeek dayOfWeek = getDayOfWeek(day);
                        LocalDate date = getNthWeekday(year, month, nthWeek, dayOfWeek);
                        if (date != null) {
                            garbageDates.add(date);
                        }
                    }
                }
            } else if (schedule.getScheduleType().equals("weekly")) {
                for (String day : schedule.getSelectedDays()) {
                    DayOfWeek dayOfWeek = getDayOfWeek(day);
                    garbageDates.addAll(getWeeklyDays(year, month, dayOfWeek));
                }
            }
        }

        return garbageDates;
    }

    // 📌 曜日を DayOfWeek に変換
    public static DayOfWeek getDayOfWeek(String japaneseDay) {
        switch (japaneseDay) {
            case "月曜日": return DayOfWeek.MONDAY;
            case "火曜日": return DayOfWeek.TUESDAY;
            case "水曜日": return DayOfWeek.WEDNESDAY;
            case "木曜日": return DayOfWeek.THURSDAY;
            case "金曜日": return DayOfWeek.FRIDAY;
            case "土曜日": return DayOfWeek.SATURDAY;
            case "日曜日": return DayOfWeek.SUNDAY;
            default: throw new IllegalArgumentException("無効な曜日: " + japaneseDay);
        }
    }
}
