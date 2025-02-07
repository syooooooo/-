package utils;

import java.time.LocalDate;

public class ExpensetureBean {
    private int id;
    private String user_id;
    private String genre;
    private String title;
    private int amount;
    private LocalDate date;
    private int day;

    // コンストラクタ
    public ExpensetureBean(String user_id, String genre, String title, int amount, int day) {
        this.user_id = user_id;
        this.genre = genre;
        this.title = title;
        this.amount = amount;
        this.day = day;
    }

    public ExpensetureBean(int id, String genre, String title, int amount, int day) {
        this.id = id;
        this.genre = genre;
        this.title = title;
        this.amount = amount;
        this.day = day;
    }

    @Override
    public String toString() {
        return "ExpensetureBean{" +
                "userId='" + user_id + '\'' +
                ", genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", day=" + day +
                ", id=" + id +
                '}';
    }

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

    // ゲッターとセッター
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}