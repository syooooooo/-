package utils;

import java.util.Arrays;

public class ScheduleBean {
    private int id;
    private String user_id;
    private String title;
    private String scheduleType;
    private String[] selectedDays;
    private String[] selectedWeeks;

    public ScheduleBean(int id, String user_id, String title, String[] selectedDays, String[] selectedWeeks) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.selectedDays = selectedDays;
        this.selectedWeeks = selectedWeeks;
    }


    public ScheduleBean() {

    }

    // Getter と Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScheduleType() {
        return scheduleType != null ? scheduleType : "weekly";
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = (scheduleType != null) ? scheduleType : "weekly";;
    }

    public String[] getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(String[] selectedDays) {
        this.selectedDays = selectedDays;
    }

    public String[] getSelectedWeeks() {
        return selectedWeeks;
    }

    public void setSelectedWeeks(String[] selectedWeeks) {
        this.selectedWeeks = selectedWeeks;
    }

    @Override
    public String toString() {
        return "ScheduleBean{id=" + id + ", title='" + title + "', weeks='" + Arrays.toString(selectedWeeks) + "', weekdays='" + Arrays.toString(selectedDays) + "'}";
    }

}